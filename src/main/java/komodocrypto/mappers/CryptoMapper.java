package komodocrypto.mappers;

import komodocrypto.model.cryptocompare.historical_data.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CryptoMapper {

    String fieldsValuesPeriodData =
            "(`time`, `from_currency`, `to_currency`, `exchange`, `open`, `low`, `high`, `close`, `average`, `volume_from`, `volume_to`) " +
            "VALUES (#{time}, #{fromCurrency}, #{toCurrency}, #{exchange}, #{open}, #{low}, #{high}, #{close}, #{average}, #{volumeFrom}, #{volumeTo});";

    String INSERT_PRICE_DAILY = "INSERT INTO `komodo_crypto`.`daily` " + fieldsValuesPeriodData;
    String INSERT_PRICE_HOURLY = "INSERT INTO `komodo_crypto`.`hourly` " + fieldsValuesPeriodData;
    String INSERT_PRICE_MINUTELY = "INSERT INTO `komodo_crypto`.`minutely` " + fieldsValuesPeriodData;

    String SELECT_PRICE_DAILY = "SELECT * FROM komodo_crypto.daily;";
    String SELECT_PRICE_HOURLY = "SELECT * FROM komodo_crypto.daily;";
    String SELECT_PRICE_MINUTELY = "SELECT * FROM komodo_crypto.daily;";

    String SELECT_TIME_DAILY = "SELECT time FROM komodo_crypto.daily " +
            "WHERE from_currency = #{fromCurrency} AND to_currency = #{toCurrency} AND exchange = #{exchange} " +
            "ORDER BY time ASC;";
    String SELECT_TIME_HOURLY = "SELECT time FROM komodo_crypto.hourly " +
            "WHERE from_currency = #{fromCurrency} AND to_currency = #{toCurrency} AND exchange = #{exchange} " +
            "ORDER BY time ASC;";
    String SELECT_TIME_MINUTELY = "SELECT time FROM komodo_crypto.minutely " +
            "WHERE from_currency = #{fromCurrency} AND to_currency = #{toCurrency} AND exchange = #{exchange} " +
            "ORDER BY time ASC;";

    // Adds historical data by time period.
    @Insert(INSERT_PRICE_DAILY)
    public int addPriceDaily(Data data);

    @Insert(INSERT_PRICE_HOURLY)
    public int addPriceHourly(Data data);

    @Insert(INSERT_PRICE_MINUTELY)
    public int addPriceMinutely(Data data);

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
}
