package komodocrypto.services.data_collection;

import komodocrypto.exceptions.custom_exceptions.TableEmptyException;
import komodocrypto.mappers.CryptoMapper;
import komodocrypto.model.RootResponse;
import komodocrypto.model.cryptocompare.historical_data.Data;
import komodocrypto.model.cryptocompare.historical_data.PriceHistorical;
import komodocrypto.model.cryptocompare.news.News;
import komodocrypto.model.cryptocompare.social_stats.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
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

    @Autowired
    ScheduledTasks scheduledTasks;

    @Autowired
    private TaskExecutor taskExecutor;

    // The list of trading pairs
    private static String[][] tradingPairs = {
            {"ETH", "BTC"},
            {"BCH", "BTC"},
            {"LTC", "BTC"},
            {"XRP", "BTC"}
    };

    // The list of exchanges
    private static String[] exchanges = {
            "Binance",
            "Bitstamp",
            "Bittrex",
            "Kraken",
            "Coinbase"
    };

    // The time periods to query for
    private static String[] periods = {
            "day",
            "hour",
            "minute"
    };

    final static int HOURS_IN_DAY = 24;
    final static int MIN_IN_HOUR = 60;
    final static int SEC_IN_MIN = 60;

    // The number of records to return. Initialized at 1, using daily as the default period.
    private int numDailyRecords = 1;

    // Loads a specified number of records from the CryptoCompare API into the database by period.
    public RootResponse backloadData(String period, int numRecords) {

        // As the CryptoCompare API calls take some time, this is executed as a background task in a new thread.
        taskExecutor.execute(new Runnable() {

            @Override
            public void run() {

                // Cycles through each trading pair.
                for (String[] pair : tradingPairs) {

                    // Cycles through each exchange.
                    for (String exchange : exchanges) {

                        // Skips Coinbase for the XRP/BTC pair.
                        if ((pair[0].equals("XRP") || pair[1].equals("XRP"))
                                && exchange.equals("Coinbase")) continue;

                        /*  if (query count(id)) >= numRecords
                                continue
                            else is count(id) < numRecords?
                                what is the last timestamp where count(id) = numRecords? (last)
                                for (i = 1; i <= numRecords - count(id); i++)
                                    add i * last * secsInPeriod to an arraylist
                                query for missing historcal data
                         */

                        // Queries by the period specified in the method signature if the number of time periods exceeds
                        // the number of records for this particular period, pair, and exchange in the database.

                        // Timestamps to query for
                        ArrayList<Integer> missingTimestamps = new ArrayList<>();

                        // The number of records in the database for this period, pair, and exchange.
                        int countRecords = countRecordsByPeriod(period, pair[0], pair[1], exchange);

                        // Checks if the number of records in the database exceeds the number of records requested.
                        if (countRecords >= numRecords) continue;
                        else {

                            // Gets the last timestamp in the database table.
                            int lastTimestamp = getLastTimestampByPeriod(period, pair[0], pair[1], exchange);

                            // The difference between the number of records requested and the number of records in the DB.
                            int diffNumRecords = numRecords - countRecords;

                            // Adds the timestamps for the net number of requested records for an array list.
                            for (int i = 1; i <= diffNumRecords; i++) {

                                int missingTimestamp = lastTimestamp - i * getPeriodLength(period);
                                missingTimestamps.add(missingTimestamp);
                            }
                        }

                        // Queries for the missing timestamps, adds the results to the database.
                        queryMissingHistoricalData(missingTimestamps, period, pair[0], pair[1], exchange);

                        // Clears the array list of missing timestamps.
                        missingTimestamps.clear();

                        // Finds and fills any gaps in the data.
                        findHistoricalGaps(period, pair[0], pair[1], exchange);
                    }
                }
            }
        });

        return new RootResponse(HttpStatus.OK, "Loading into the database. This is the data currently available.",
                getResponseData());
    }

    // Executes the appropriate tasks depending on which cron job has just run.
    public RootResponse switchCronOps(String period) {

        // Queries for historical data for each trading pair from each exchange.
        // NOTE: Coinbase does not support XRP/BTC, so it is skipped for this pair.

        // Cycles through each trading pair.
        for (String[] pair : tradingPairs) {

            // Cycles through each exchange.
            for (String exchange : exchanges) {

                // Skips Coinbase for the XRP/BTC pair.
                if ((pair[0].equals("XRP") || pair[1].equals("XRP"))
                        && exchange.equals("Coinbase")) continue;

                if (scheduledTasks.isCronHit() == true) {

                    switch (period) {
                        case "week":
                            aggregateWeekly(pair[0], pair[1], exchange);
                            continue;
                        case "day":
                            queryMissingHistoricalData(scheduledTasks.getTimestampDaily(), period, pair[0], pair[1], exchange);
                            continue;
                        case "hour":
                            queryMissingHistoricalData(scheduledTasks.getTimestampHourly(), period, pair[0], pair[1], exchange);
                            continue;
                        case "minute":
                            queryMissingHistoricalData(scheduledTasks.getTimestampMinutely(), period, pair[0], pair[1], exchange);
                            continue;
                    }
                }
            }
        }

        return new RootResponse(HttpStatus.OK, "Data successfully added.", getResponseData());
    }

    // Queries for historical data.
    public void queryHistoricalData(String period, String fromCurrency, String toCurrency, String exchange, int numRecords) {

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
                    "&e=" + exchange +
                    "&toTs=" + timestamp +
                    "&aggregate=1&limit=" + numRecords;
            PriceHistorical historicalData = restTemplate.getForObject(query, PriceHistorical.class);

            // The Data object being acted upon. Want the second element in the Data array because the CryptoCompare
            // API returns at least 2, even if the limit is set to 1, and the second is the desired element.
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

        periodLength = getPeriodLength(period);

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

    // Aggregates daily data into weekly data.
    public void aggregateWeekly(String fromCurrency, String toCurrency, String exchange) {

        int weeklyTimestamp = scheduledTasks.getTimestampWeekly();
        int secInWeek = SEC_IN_MIN * MIN_IN_HOUR * HOURS_IN_DAY * 7;

        cryptoMapper.aggregateWeekly(weeklyTimestamp - secInWeek, weeklyTimestamp, fromCurrency, toCurrency, exchange);
    }

    // Adds social data.
    public RootResponse addSocial() {

        int time = (int) (System.currentTimeMillis() / 1000);
        int[] currencyIds = {7605, 202330, 3808, 5031};

        for (int i = 0; i < currencyIds.length; i++) {
            String query = "https://www.cryptocompare.com/api/data/socialstats/?id=" + currencyIds[i];
            SocialStats social = restTemplate.getForObject(query, SocialStats.class);

            Twitter twitterStats = social.getData().getTwitter();
            Reddit redditStats = social.getData().getReddit();
            Facebook facebookStats = social.getData().getFacebook();

            twitterStats.setCurrency(tradingPairs[i][0]);
            twitterStats.setTime(time);

            redditStats.setCurrency(tradingPairs[i][0]);
            redditStats.setTime(time);

            facebookStats.setCurrency(tradingPairs[i][0]);
            facebookStats.setTime(time);

            cryptoMapper.addTwitter(twitterStats);
            cryptoMapper.addReddit(redditStats);
            cryptoMapper.addFacebook(facebookStats);
        }

        SocialResponse socialResponse = new SocialResponse();
        socialResponse.setTwitter(cryptoMapper.getTwitter());
        socialResponse.setReddit(cryptoMapper.getReddit());
        socialResponse.setFacebook(cryptoMapper.getFacebook());

        return new RootResponse(HttpStatus.OK, "Social media data successfully added.", socialResponse);
    }

    // Counts the number of records by period and other criteria.
    public int countRecordsByPeriod(String period, String fromCurrency, String toCurrency, String exchange) {

        switch(period) {
            case "day":
                return cryptoMapper.countRecordsDaily(fromCurrency, toCurrency, exchange);
            case "hour":
                return cryptoMapper.countRecordsHourly(fromCurrency, toCurrency, exchange);
            default:
                return cryptoMapper.countRecordsMinutely(fromCurrency, toCurrency, exchange);
        }
    }

    // Gets the latest timestamp in the database table by time period and other criteria.
    public int getLastTimestampByPeriod(String period, String fromCurrency, String toCurrency, String exchange) {

        switch(period) {
            case "day":
                return cryptoMapper.getLastTimestampDaily(fromCurrency, toCurrency, exchange);
            case "hour":
                return cryptoMapper.getLastTimestampHourly(fromCurrency, toCurrency, exchange);
            default:
                return cryptoMapper.getLastTimestampMinutely(fromCurrency, toCurrency, exchange);
        }
    }

    // Adds news stories to the database.
    public RootResponse addNews(String categories) {

        // Maps even if categories is empty or nonsense.
        String query = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN&categories=" + categories;
        News news = restTemplate.getForObject(query, News.class);
        komodocrypto.model.cryptocompare.news.Data[] newsData = news.getData();

        for (komodocrypto.model.cryptocompare.news.Data story : newsData) {
            cryptoMapper.addNews(story);
        }

        return new RootResponse(HttpStatus.OK, "News data successfully added.", cryptoMapper.getNews());
    }

    // Gets news data from the database.
    public RootResponse getNews(String categories) throws TableEmptyException {

        if (cryptoMapper.getNews().length == 0) {

            throw new TableEmptyException();

        } else /*if (categories == null)*/{

            komodocrypto.model.cryptocompare.news.Data[] newsData = cryptoMapper.getNews();
            return new RootResponse(HttpStatus.OK, "News data successfully queried.", newsData);

//        } else {
//
//            // Gets a list of news stories by each of the user-input categories.
//            // NOTE: Can only query the database for one category at a time for now, not because multiple categories
//            // aren't possible, but because I'm not sure how to do it at the moment. Will look into it though.
//
//            // Holds the arrays of news stories by category.
//            ArrayList<komodocrypto.model.cryptocompare.news.Data[]> newsData = new ArrayList<>();
//
//            // Splits the user-input category string along commas. If the string was not delimited by commas or contained
//            // nonsense, a set of news articles will still be returned, just not the desired ones.
//            // NOTE: Look at MySQL stored procedures.
//            String[] categoryArray = categories.split(",");
//            for (String category : categoryArray) {
//
//                newsData.add(cryptoMapper.getNewsByCategory(category));
//            }
//
//            // Removes duplicate elements.
//            // Do this later.
//
//            return new GeneralResponse(newsData);
        }
    }

    
    // Gets an array of price data depending on the pair/exchange combination.
    public Data[] getDataByPeriodConditional(String period, String fromCurrency, String toCurrency, String exchange) {

        switch (period) {
            case "day":
                return cryptoMapper.getPriceDailyConditional(fromCurrency, toCurrency, exchange);
            case "hour":
                return cryptoMapper.getPriceHourlyConditional(fromCurrency, toCurrency, exchange);
            default:
                return cryptoMapper.getPriceMinutelyConditional(fromCurrency, toCurrency, exchange);
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

    // Gets the number of seconds in a specified period.
    public int getPeriodLength(String period) {

        switch (period) {
            case "day":
                return HOURS_IN_DAY * MIN_IN_HOUR * SEC_IN_MIN;
            case "hour":
                return MIN_IN_HOUR * SEC_IN_MIN;
            default:
                return MIN_IN_HOUR;
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
    
    public RootResponse getDataByPeriod(String period) {
        switch (period) {
            case "day":
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getPriceDaily());
            case "hour":
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getPriceHourly());
            default:
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getPriceMinutely());
        }
    }

    public RootResponse getDataByCurrency(String currency) {
        return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataByCurrency(currency));
    }

    public RootResponse getDataByExchange(String exchange) {
        return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataByExchange(exchange));
    }

    public RootResponse getDataByPeriodAndCurrency(String period, String currency) {
        switch (period) {
            case "day":
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataDailyByCurrency(currency));
            case "hour":
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataHourlyByCurrency(currency));
            default:
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataMinutelyByCurrency(currency));
        }
    }

    public RootResponse getDataByPeriodAndExchange(String period, String exchange) {
        switch (period) {
            case "day":
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataDailyByExchange(exchange));
            case "hour":
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataHourlyByExchange(exchange));
            default:
                return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataMinutelyByExchange(exchange));
        }
    }

    public RootResponse getDataByCurrencyAndExchange(String currency, String exchange) {
        return new RootResponse(HttpStatus.OK, "Query successful", cryptoMapper.getDataByCurrencyAndExchange(currency, exchange));
    }
}
