package komodocrypto.services.data_collection;

import komodocrypto.mappers.CryptoMapper;
import komodocrypto.model.GeneralResponse;
import komodocrypto.model.cryptocompare.historical_data.Data;
import komodocrypto.model.cryptocompare.historical_data.PriceHistorical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class CryptoCompareHistoricalService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CryptoMapper cryptoMapper;

    // The list of trading pairs
    private String[][] tradingPairs = {
            {"ETH", "BTC"},
            {"BCH", "BTC"},
            {"LTC", "BTC"},
            {"XRP", "BTC"}
    };

    // The list of exchanges
    private String[] exchanges = {
            "Binance",
            "Bitstamp",
            "Bittrex",
            "Kraken",
            "Coinbase"
    };

    // The time periods to query for
    private String[] periods = {
            "day",
            "hour",
            "minute"
    };

    final int HOURS_IN_DAY = 24;
    final int MIN_IN_HOUR = 60;
    final int SEC_IN_MIN = 60;

    // The number of records to return. Initialized at 1, using daily as the default period.
    private int numDailyRecords = 1;

    // Switches between data operations for each pair/exchange combo depending on specified conditions.
    public GeneralResponse switchDataOperations() {

        // The object to return
        GeneralResponse response = new GeneralResponse();

        // Queries for historical data for each trading pair from each exchange.
        // NOTE: Coinbase does not support XRP/BTC, so it is skipped for this pair.
        for (String[] pair : tradingPairs) {

            for (String exchange : exchanges) {

                // Skips Coinbase for the XRP/BTC pair.
                if ((pair[0].equals("XRP") || pair[1].equals("XRP")) && exchange.equals("Coinbase")) continue;

                // Queries for daily, hourly, and minutely data
                for (String period : periods) {

                    // Determines whether the database table referencing this period is empty.
                    Data[] dataByPeriod = getDataByPeriod(period);

                    // If the table is empty, backfill. Otherwise, find gaps in the data.
                    if (dataByPeriod.length == 0) {
                        queryHistoricalData(period, pair[0], pair[1], exchange);
                    } else {
                        findHistoricalGaps(period, pair[0], pair[1], exchange);
                    }
                }
            }
        }

        // Combines the data from daily, hourly, and minutely tables into a large Data array and adds to a response
        // object for display purposes.
        response.setData(getResponseData());

        return response;
    }

    // Queries for historical data.
    public void queryHistoricalData(String period, String fromCurrency, String toCurrency, String exchange) {

        // This keeps the value of the master period unchanged.
        int numRecords = numDailyRecords;

        // Ensures that records for each hour and minute are returned for each day.
        if (period.equals("hour")) numRecords *= HOURS_IN_DAY;
        else if (period.equals("minute")) numRecords *= HOURS_IN_DAY * MIN_IN_HOUR;

        String query = "https://min-api.cryptocompare.com/data/histo" + period + "?" +
                "fsym=" + fromCurrency +
                "&tsym=" + toCurrency +
                "&e=" + exchange +
                "&aggregate=1&limit=" + numRecords;

        PriceHistorical historicalData = restTemplate.getForObject(query, PriceHistorical.class);

        // Adds the pair names, the exchange name, the average trading price, and the queried historical
        // data to the desired database table depending on the time period.
        int length = historicalData.getData().length;

        for (int i = 0; i < length; i++) {

            Data data = historicalData.getData()[i];

            // Adds the data to the database.
            addHistoricalData(data, period, fromCurrency, toCurrency, exchange);
        }
    }

    // Queries for the missing historical data.
    public void queryMissingHistoricalData(ArrayList<Integer> missingTimestamps, String period, String fromCurrency,
                                         String toCurrency, String exchange) {

        // This keeps the value of the master period unchanged.
        int numRecords = numDailyRecords;

        for (Integer timestamp : missingTimestamps) {

            String query = "https://min-api.cryptocompare.com/data/histo" + period + "?" +
                    "fsym=" + fromCurrency +
                    "&tsym=" + toCurrency +
                    "&toTs=" + timestamp +
                    "&aggregate=1&limit=" + numRecords;
            PriceHistorical historicalData = restTemplate.getForObject(query, PriceHistorical.class);

            // The Data object being acted upon. Want the first one because the CryptoCompare API returns at
            // least 2, even if the limit is set to 1.
            Data data = historicalData.getData()[1];

            // Adds the data to the database.
            addHistoricalData(data, period, fromCurrency, toCurrency, exchange);
        }
    }

    // Adds historical data.
    public void addHistoricalData(Data data, String period, String fromCurrency, String toCurrency, String exchange) {

        // Adds the pair names to the Data object.
        data.setFromCurrency(fromCurrency);
        data.setToCurrency(toCurrency);

        // Adds the exchange name to the Data object.
        data.setExchange(exchange);

        // Adds the average trading price to the Data object.
        BigDecimal high = data.getHigh();
        BigDecimal low = data.getLow();
        data.setAverage(averagePriceHistorical(high, low));

        // Adds the historical data depending on the time period.
        if (period.equals("day")) cryptoMapper.addPriceDaily(data);
        else if (period.equals("hour")) cryptoMapper.addPriceHourly(data);
        else if (period.equals("minute")) cryptoMapper.addPriceMinutely(data);
    }

    // Finds gaps in the database.
    public void findHistoricalGaps(String period, String fromCurrency, String toCurrency, String exchange) {

        // The array list of missing timestamps, if any
        ArrayList<Integer> missingTimestamps = new ArrayList<>();
        int periodLength;

        // Gets an array of timestamps depending on the pair/exchange combination.
        Integer[] timestamps = getTimestampsByPeriod(period, fromCurrency, toCurrency, exchange);

        switch (period) {
            case "day":
                periodLength = HOURS_IN_DAY * MIN_IN_HOUR * SEC_IN_MIN;
                break;
            case "hour":
                periodLength = MIN_IN_HOUR * SEC_IN_MIN;
                break;
            default:
                periodLength = MIN_IN_HOUR;
        }

        // Finds missing timestamps and adds them to an array list.
        for (int i = 0; i < timestamps.length - 1; i++) {

            int difference = timestamps[i + 1] - timestamps[i];
            int periodsMissing = difference / periodLength;

            // Adds the missing timestamps, if any, to an array list.
            if (periodsMissing > 0) {

                // Must be less than the number of periods missing as this value is the next found value in
                // the table.
                for (int j = 1; j < periodsMissing; j++) {
                    missingTimestamps.add(timestamps[i] + periodLength * j);
                }
            }
        }

        // Adds the missing data to the proper table.
        if (missingTimestamps.size() > 0) queryMissingHistoricalData(missingTimestamps, period, fromCurrency,
                toCurrency, exchange);
    }

    // Averages the high and low price.
    public BigDecimal averagePriceHistorical(BigDecimal high, BigDecimal low) {

        BigDecimal average;
        BigDecimal sum;
        BigDecimal divisor = BigDecimal.valueOf(2.0);

        sum = high.add(low);
        average = sum.divide(divisor);

        return average;
    }

    // Gets an array of price data depending on the pair/exchange combination.
    public Data[] getDataByPeriod(String period) {

        switch (period) {
            case "day":
                return cryptoMapper.getPriceDaily();
            case "hour":
                return cryptoMapper.getPriceHourly();
            default:
                return cryptoMapper.getPriceMinutely();
        }
    }

    // Gets an array of timestamps depending on the pair/exchange combination.
    public Integer[] getTimestampsByPeriod(String period, String fromCurrency, String toCurrency, String exchange) {

        switch (period) {
            case "day":
                return cryptoMapper.getTimeDaily(fromCurrency, toCurrency, exchange);
            case "hour":
                return cryptoMapper.getTimeHourly(fromCurrency, toCurrency, exchange);
            default:
                return cryptoMapper.getTimeMinutely(fromCurrency, toCurrency, exchange);
        }
    }

    // Combines the data from daily, hourly, and minutely tables into a large Data array for response purposes.
    public Data[] getResponseData() {

        // Gets the daily, hourly, and minutely data.
        Data[] dailyData = cryptoMapper.getPriceDaily();
        Data[] hourlyData = cryptoMapper.getPriceHourly();
        Data[] minutelyData = cryptoMapper.getPriceMinutely();

        // Adds the daily, hourly, and minutely data into a new single array.
        Data[] historicalData = new Data[dailyData.length + hourlyData.length + minutelyData.length];

        for (int i = 0; i < dailyData.length; i++) {
            historicalData[i] = dailyData[i];
        }

        for (int i = 0; i < hourlyData.length; i++) {
            historicalData[i] = hourlyData[i];
        }

        for (int i = 0; i < minutelyData.length; i++) {
            historicalData[i] = minutelyData[i];
        }

        return historicalData;
    }
}
