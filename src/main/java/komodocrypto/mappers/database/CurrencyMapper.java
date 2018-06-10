package komodocrypto.mappers.database;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CurrencyMapper {

    String SELECT_ID_BY_SYMBOL = "SELECT `currency_id` from `komodoDB`.`currency` WHERE `symbol` = #{symbol};";

    @Select(SELECT_ID_BY_SYMBOL)
    public int getIdBySymbol(String symbol);
}
