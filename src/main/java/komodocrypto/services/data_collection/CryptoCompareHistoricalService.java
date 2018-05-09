package komodocrypto.services.data_collection;

import komodocrypto.mappers.CryptoMapper;
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

    /*  What methods do we need?
            addPriceHistorical
            averagePriceHistorical

        Need exchange name and from/toCurrency to go in the database as well.
     */

    public void addPriceHistorical() {

        // The list of trading pairs
        String[][] tradingPairs = {
                {"ETH", "BTC"},
                {"BCH", "BTC"},
                {"LTC", "BTC"},
                {"XRP", "BTC"}
        };

        // The list of exchanges
        String[] exchanges = {
                "Binance",
                "Bitstamp",
                "Bittrex",
                "Kraken",
                "Coinbase"
        };

        // The time periods to query for
        String[] periods = {
                "day",
                "hour",
                "minute"
        };

        // The number of daily records to return as well constants that ensure that the equivalent number of hourly and
        // minutely records are queried for the same time period.
        int numDailyRecords = 5;
        final int HOURS_IN_DAY = 24;
        final int MIN_IN_HOUR = 60;

        // Queries for historical data for each trading pair from each exchange.
        // NOTE: Coinbase does not support XRP/BTC, so it is skipped for this pair.
        for (String[] pair : tradingPairs) {

            for (String exchange : exchanges) {

                // Skips Coinbase for the XRP/BTC pair.
                if ((pair[0].equals("XRP") || pair[1].equals("XRP")) && exchange.equals("Coinbase")) continue;

                // Queries for daily, hourly, and minutely data
                for (String period : periods) {

                    // The number of records to return
                    int numRecords = numDailyRecords;

                    // Ensures that records for each hour and minute are returned for each day.
                    if (period.equals("hour")) numRecords *= HOURS_IN_DAY;
                    else if (period.equals("minute")) numRecords *= HOURS_IN_DAY * MIN_IN_HOUR;

                    String query = "https://min-api.cryptocompare.com/data/histo" + period + "?" +
                            "fsym=" + pair[0] +
                            "&tsym=" + pair[1] +
                            "&e=" + exchange +
                            "&aggregate=1&limit=" + numRecords;

                    PriceHistorical historicalData = restTemplate.getForObject(query, PriceHistorical.class);

                    // Adds the historical data to the desired database table depending on the time period.
//                    if (period.equals("day")) cryptoMapper.addPriceDaily(historicalData);
//                    else if (period.equals("hour")) cryptoMapper.addPriceHourly(historicalData);
//                    else if (period.equals("minute")) cryptoMapper.addPriceMinutely(historicalData);
                }
            }
        }
    }

    public double averagePriceHistorical(double high, double low) {

        return (high + low) / 2;
    }
}
