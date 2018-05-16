package komodocrypto.mappers;

import komodocrypto.model.cryptocompare.historical_data.Data;
import komodocrypto.model.cryptocompare.social_stats.Facebook;
import komodocrypto.model.cryptocompare.social_stats.Reddit;
import komodocrypto.model.cryptocompare.social_stats.Twitter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CryptoMapper {

    String fieldsValuesPeriodData =
            "(`time`, `fromCurrency`, `toCurrency`, `exchange`, `open`, `low`, `high`, `close`, `average`, `volumeFrom`, `volumeTo`) " +
            "VALUES (#{time}, #{fromCurrency}, #{toCurrency}, #{exchange}, #{open}, #{low}, #{high}, #{close}, #{average}, #{volumeFrom}, #{volumeTo});";

    String INSERT_PRICE_DAILY = "INSERT INTO `komodo_crypto`.`daily` " + fieldsValuesPeriodData;
    String INSERT_PRICE_HOURLY = "INSERT INTO `komodo_crypto`.`hourly` " + fieldsValuesPeriodData;
    String INSERT_PRICE_MINUTELY = "INSERT INTO `komodo_crypto`.`minutely` " + fieldsValuesPeriodData;

    String SELECT_PRICE_DAILY = "SELECT * FROM komodo_crypto.daily;";
    String SELECT_PRICE_HOURLY = "SELECT * FROM komodo_crypto.hourly;";
    String SELECT_PRICE_MINUTELY = "SELECT * FROM komodo_crypto.minutely;";

    String SELECT_TIME_DAILY = "SELECT time FROM komodo_crypto.daily " +
            "WHERE fromCurrency = #{arg0} AND toCurrency = #{arg1} AND exchange = #{arg2} " +
            "ORDER BY time ASC;";
    String SELECT_TIME_HOURLY = "SELECT time FROM komodo_crypto.hourly " +
            "WHERE fromCurrency = #{arg0} AND toCurrency = #{arg1} AND exchange = #{arg2} " +
            "ORDER BY time ASC;";
    String SELECT_TIME_MINUTELY = "SELECT time FROM komodo_crypto.minutely " +
            "WHERE fromCurrency = #{arg0} AND toCurrency = #{arg1} AND exchange = #{arg2} " +
            "ORDER BY time ASC;";

    String INSERT_PRICE_AGGREGATED_WEEKLY = "INSERT INTO komodo_crypto.weekly " +
            "(`time`, `fromCurrency`, `toCurrency`, `exchange`, `open`, `low`, `high`, `close`, `average`, `volumeFrom`, `volumeTo`) " +
            "VALUES (" +
                "#{arg1}, " +
                "#{arg2}, " +
                "#{arg3}, " +
                "#{arg4}, " +
                "(SELECT open FROM komodo_crypto.hourly WHERE time = #{arg0}), " +
                "(SELECT MIN(low) FROM komodo_crypto.hourly WHERE (time >= #{arg0} AND time <= #{arg1})), " +
                "(SELECT MAX(high) FROM komodo_crypto.hourly WHERE (time >= #{arg0} AND time <= #{arg1})), " +
                "(SELECT close FROM komodo_crypto.hourly WHERE time = #{arg1}), " +
                "(SELECT AVG(average) FROM komodo_crypto.hourly WHERE (time >= #{arg0} AND time <= #{arg1})), " +
                "(SELECT volumeFrom FROM komodo_crypto.hourly WHERE time = #{arg0}), " +
                "(SELECT volumeTo FROM komodo_crypto.hourly WHERE time = #{arg1}) " +
            ");";

    String INSERT_TWITTER_DATA = "INSERT INTO `komodo_crypto`.`twitter` " +
            "(`time`, `currency`, `statuses`, `followers`, `favorites`, `lists`, `following`, `points`) " +
            "VALUES (#{time}, #{currency}, #{statuses}, #{followers}, #{favorites}, #{lists}, #{following}, #{points});";
    String INSERT_REDDIT_DATA = "INSERT INTO `komodo_crypto`.`reddit` " +
            "(`time`, `currency`, `subscribers`, `commentsPerDay`, `commentsPerHour`, `activeUsers`, `postsPerDay`, `postsPerHour`, `points`) " +
            "VALUES (#{time}, #{currency}, #{subscribers}, #{commentsPerDay}, #{commentsPerHour}, #{activeUsers}, #{postsPerDay}, #{postsPerHour}, #{points});";
    String INSERT_FACEBOOK_DATA = "INSERT INTO `komodo_crypto`.`facebook` " +
            "(`time`, `currency`, `talkingAbout`, `likes`, `points`) " +
            "VALUES (#{time}, #{currency}, #{talkingAbout}, #{likes}, #{points});";

    String SELECT_TWITTER_DATA = "SELECT * FROM komodo_crypto.twitter;";
    String SELECT_REDDIT_DATA = "SELECT * FROM komodo_crypto.reddit;";
    String SELECT_FACEBOOK_DATA = "SELECT * FROM komodo_crypto.facebook;";

    String INSERT_NEWS_DATA = "INSERT IGNORE INTO `komodo_crypto`.`news` " +
            "(`articleId`, `publishedOn`, `title`, `url`, `body`, `tags`, `categories`) " +
            "VALUES (#{articleId}, #{publishedOn}, #{title}, #{url}, #{body}, #{tags}, #{categories});";
    String SELECT_ALL_NEWS_DATA = "SELECT * FROM `komodo_crypto`.`news`;";
    String SELECT_NEWS_BY_CATEGORY = "SELECT * FROM `komodo_crypto`.`news` WHERE categories LIKE CONCAT('\'%', ${category}, '%\');";


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


    // Gets historical data by time period.
    @Select(SELECT_PRICE_DAILY)
    public Data[] getPriceDaily();

    @Select(SELECT_PRICE_HOURLY)
    public Data[] getPriceHourly();

    @Select(SELECT_PRICE_MINUTELY)
    public Data[] getPriceMinutely();


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

//    @Select(SELECT_NEWS_BY_CATEGORY)
//    public komodocrypto.model.cryptocompare.news.Data[] getNewsByCategory(String category);

}
