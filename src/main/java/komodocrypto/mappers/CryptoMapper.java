package komodocrypto.mappers;

import komodocrypto.model.cryptocompare.historical_data.Data;
import komodocrypto.model.cryptocompare.social_stats.Facebook;
import komodocrypto.model.cryptocompare.social_stats.Reddit;
import komodocrypto.model.cryptocompare.social_stats.Twitter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CryptoMapper {

    String fieldsValuesPeriodData =
            "(`time`, `fromCurrency`, `toCurrency`, `exchange`, `open`, `low`, `high`, `close`, `average`, `volumeFrom`, `volumeTo`) " +
            "VALUES (#{time}, #{fromCurrency}, #{toCurrency}, #{exchange}, #{open}, #{low}, #{high}, #{close}, #{average}, #{volumeFrom}, #{volumeTo});";

    String INSERT_PRICE_DAILY = "INSERT IGNORE INTO `komodoDB`.`daily` " + fieldsValuesPeriodData;
    String INSERT_PRICE_HOURLY = "INSERT IGNORE INTO `komodoDB`.`hourly` " + fieldsValuesPeriodData;
    String INSERT_PRICE_MINUTELY = "INSERT IGNORE INTO `komodoDB`.`minutely` " + fieldsValuesPeriodData;

    String SELECT_PRICE_DAILY = "SELECT * FROM komodoDB.daily;";
    String SELECT_PRICE_HOURLY = "SELECT * FROM komodoDB.hourly;";
    String SELECT_PRICE_MINUTELY = "SELECT * FROM komodoDB.minutely;";

    String SELECT_PRICE_DAILY_CONDITIONAL = "SELECT * FROM `komodoDB`.`daily` " +
            "WHERE fromCurrency = #{param1} AND toCurrency = #{param2} AND exchange = #{param3};";
    String SELECT_PRICE_HOURLY_CONDITIONAL = "SELECT * FROM `komodoDB`.`hourly` " +
            "WHERE fromCurrency = #{param1} AND toCurrency = #{param2} AND exchange = #{param3};";
    String SELECT_PRICE_MINUTELY_CONDITIONAL = "SELECT * FROM `komodoDB`.`minutely` " +
            "WHERE fromCurrency = #{param1} AND toCurrency = #{param2} AND exchange = #{param3};";

    String SELECT_DATA_BY_CURRENCY = "SELECT * FROM komodoDB.daily WHERE fromCurrency = #{currency} " +
            "UNION SELECT * FROM komodoDB.hourly WHERE fromCurrency = #{currency} " +
            "UNION SELECT * FROM komodoDB.minutely WHERE fromCurrency = #{currency};";
    String SELECT_DATA_BY_EXCHANGE = "SELECT * FROM komodoDB.daily WHERE exchange = #{exchange} " +
            "UNION SELECT * FROM komodoDB.hourly WHERE exchange = #{exchange} " +
            "UNION SELECT * FROM komodoDB.minutely WHERE exchange = #{exchange};";
    String SELECT_DATA_DAILY_BY_CURRENCY = "SELECT * FROM komodoDB.daily WHERE fromCurrency = #{currency};";
    String SELECT_DATA_HOURLY_BY_CURRENCY = "SELECT * FROM komodoDB.hourly WHERE fromCurrency = #{currency};";
    String SELECT_DATA_MINUTELY_BY_CURRENCY = "SELECT * FROM komodoDB.minutely WHERE fromCurrency = #{currency};";
    String SELECT_DATA_DAILY_BY_EXCHANGE = "SELECT * FROM komodoDB.daily WHERE exchange = #{exchange};";
    String SELECT_DATA_HOURLY_BY_EXCHANGE = "SELECT * FROM komodoDB.hourly WHERE exchange = #{exchange};";
    String SELECT_DATA_MINUTELY_BY_EXCHANGE = "SELECT * FROM komodoDB.minutely WHERE exchange = #{exchange};";
    String SELECT_DATA_BY_CURRENCY_AND_EXCHANGE = "SELECT * FROM komodoDB.daily WHERE fromCurrency = #{arg0} AND exchange = #{arg1} " +
            "UNION SELECT * FROM komodoDB.hourly WHERE fromCurrency = #{arg0} AND exchange = #{arg1}" +
            "UNION SELECT * FROM komodoDB.minutely WHERE fromCurrency = #{arg0} AND exchange = #{arg1};";

    String SELECT_TIME_DAILY = "SELECT time FROM komodoDB.daily " +
            "WHERE fromCurrency = #{arg0} AND toCurrency = #{arg1} AND exchange = #{arg2} " +
            "ORDER BY time ASC;";
    String SELECT_TIME_HOURLY = "SELECT time FROM komodoDB.hourly " +
            "WHERE fromCurrency = #{arg0} AND toCurrency = #{arg1} AND exchange = #{arg2} " +
            "ORDER BY time ASC;";
    String SELECT_TIME_MINUTELY = "SELECT time FROM komodoDB.minutely " +
            "WHERE fromCurrency = #{arg0} AND toCurrency = #{arg1} AND exchange = #{arg2} " +
            "ORDER BY time ASC;";

    String INSERT_PRICE_AGGREGATED_WEEKLY = "INSERT IGNORE INTO komodoDB.weekly " +
            "(`time`, `fromCurrency`, `toCurrency`, `exchange`, `open`, `low`, `high`, `close`, `average`, `volumeFrom`, `volumeTo`) " +
            "VALUES (" +
                "#{arg1}, " +
                "#{arg2}, " +
                "#{arg3}, " +
                "#{arg4}, " +
                "(SELECT open FROM komodoDB.hourly WHERE time = #{arg0}), " +
                "(SELECT MIN(low) FROM komodoDB.hourly WHERE (time >= #{arg0} AND time <= #{arg1})), " +
                "(SELECT MAX(high) FROM komodoDB.hourly WHERE (time >= #{arg0} AND time <= #{arg1})), " +
                "(SELECT close FROM komodoDB.hourly WHERE time = #{arg1}), " +
                "(SELECT AVG(average) FROM komodoDB.hourly WHERE (time >= #{arg0} AND time <= #{arg1})), " +
                "(SELECT volumeFrom FROM komodoDB.hourly WHERE time = #{arg0}), " +
                "(SELECT volumeTo FROM komodoDB.hourly WHERE time = #{arg1}) " +
            ");";

    String INSERT_TWITTER_DATA = "INSERT INTO `komodoDB`.`twitter` " +
            "(`time`, `currency`, `statuses`, `followers`, `favorites`, `lists`, `following`, `points`) " +
            "VALUES (#{time}, #{currency}, #{statuses}, #{followers}, #{favorites}, #{lists}, #{following}, #{points});";
    String INSERT_REDDIT_DATA = "INSERT INTO `komodoDB`.`reddit` " +
            "(`time`, `currency`, `subscribers`, `commentsPerDay`, `commentsPerHour`, `activeUsers`, `postsPerDay`, `postsPerHour`, `points`) " +
            "VALUES (#{time}, #{currency}, #{subscribers}, #{commentsPerDay}, #{commentsPerHour}, #{activeUsers}, #{postsPerDay}, #{postsPerHour}, #{points});";
    String INSERT_FACEBOOK_DATA = "INSERT INTO `komodoDB`.`facebook` " +
            "(`time`, `currency`, `talkingAbout`, `likes`, `points`) " +
            "VALUES (#{time}, #{currency}, #{talkingAbout}, #{likes}, #{points});";

    String SELECT_TWITTER_DATA = "SELECT * FROM komodoDB.twitter;";
    String SELECT_REDDIT_DATA = "SELECT * FROM komodoDB.reddit;";
    String SELECT_FACEBOOK_DATA = "SELECT * FROM komodoDB.facebook;";

    String INSERT_NEWS_DATA = "INSERT IGNORE INTO `komodoDB`.`news` " +
            "(`articleId`, `publishedOn`, `title`, `url`, `body`, `tags`, `categories`) " +
            "VALUES (#{articleId}, #{publishedOn}, #{title}, #{url}, #{body}, #{tags}, #{categories});";
    String SELECT_ALL_NEWS_DATA = "SELECT * FROM `komodoDB`.`news`;";
    String SELECT_NEWS_BY_CATEGORY = "SELECT * FROM `komodoDB`.`news` WHERE categories LIKE '%${category}%';";


    // Adds historical data by time period.
    @Insert(INSERT_PRICE_DAILY)
    public int addPriceDaily(Data data);

    @Insert(INSERT_PRICE_HOURLY)
    public int addPriceHourly(Data data);

    @Insert(INSERT_PRICE_MINUTELY)
    public int addPriceMinutely(Data data);


    // Gets hourly data between two specified timestamps
    @Insert(INSERT_PRICE_AGGREGATED_WEEKLY)
    public int aggregateWeekly(int startTime, int endTime, String fromCurrency, String toCurrency, String exchange);


    // Adds social media stats.
    @Insert(INSERT_TWITTER_DATA)
    public int addTwitter(Twitter twitterData);

    @Insert(INSERT_REDDIT_DATA)
    public int addReddit(Reddit redditData);

    @Insert(INSERT_FACEBOOK_DATA)
    public int addFacebook(Facebook facebookData);


    // Gets social media stats.
    @Select(SELECT_TWITTER_DATA)
    public Twitter[] getTwitter();

    @Select(SELECT_REDDIT_DATA)
    public Reddit[] getReddit();

    @Select(SELECT_FACEBOOK_DATA)
    public Facebook[] getFacebook();


    // Gets all historical data by time period.
    @Select(SELECT_PRICE_DAILY)
    public Data[] getPriceDaily();

    @Select(SELECT_PRICE_HOURLY)
    public Data[] getPriceHourly();

    @Select(SELECT_PRICE_MINUTELY)
    public Data[] getPriceMinutely();


    // Gets historical data by time period, currency pair, and exchange.
    @Select(SELECT_PRICE_DAILY_CONDITIONAL)
    public Data[] getPriceDailyConditional(String fromCurrency, String toCurrency, String exchange);

    @Select(SELECT_PRICE_HOURLY_CONDITIONAL)
    public Data[] getPriceHourlyConditional(String fromCurrency, String toCurrency, String exchange);

    @Select(SELECT_PRICE_MINUTELY_CONDITIONAL)
    public Data[] getPriceMinutelyConditional(String fromCurrency, String toCurrency, String exchange);


    // Gets timestamp by period
    @Select(SELECT_TIME_DAILY)
    public Integer[] getTimeDaily(String fromCurrency, String toCurrency, String exchange);

    @Select(SELECT_TIME_HOURLY)
    public Integer[] getTimeHourly(String fromCurrency, String toCurrency, String exchange);

    @Select(SELECT_TIME_MINUTELY)
    public Integer[] getTimeMinutely(String fromCurrency, String toCurrency, String exchange);


    // Adds and retrieves unique news data.
    @Insert(INSERT_NEWS_DATA)
    public int addNews(komodocrypto.model.cryptocompare.news.Data newsData);

    @Select(SELECT_ALL_NEWS_DATA)
    public komodocrypto.model.cryptocompare.news.Data[] getNews();

    @Select(SELECT_NEWS_BY_CATEGORY)
    public komodocrypto.model.cryptocompare.news.Data[] getNewsByCategory(@Param("category") String category);



    @Select(SELECT_DATA_BY_CURRENCY)
    public Data[] getDataByCurrency(String currency);

    @Select(SELECT_DATA_BY_EXCHANGE)
    public Data[] getDataByExchange(String exchange);

    @Select(SELECT_DATA_DAILY_BY_CURRENCY)
    public Data[] getDataDailyByCurrency(String currency);

    @Select(SELECT_DATA_HOURLY_BY_CURRENCY)
    public Data[] getDataHourlyByCurrency(String currency);

    @Select(SELECT_DATA_MINUTELY_BY_CURRENCY)
    public Data[] getDataMinutelyByCurrency(String currency);

    @Select(SELECT_DATA_DAILY_BY_EXCHANGE)
    public Data[] getDataDailyByExchange(String exchange);

    @Select(SELECT_DATA_HOURLY_BY_EXCHANGE)
    public Data[] getDataHourlyByExchange(String exchange);

    @Select(SELECT_DATA_MINUTELY_BY_EXCHANGE)
    public Data[] getDataMinutelyByExchange(String exchange);

    @Select(SELECT_DATA_BY_CURRENCY_AND_EXCHANGE)
    public Data[] getDataByCurrencyAndExchange(String currency, String exchange);
}
