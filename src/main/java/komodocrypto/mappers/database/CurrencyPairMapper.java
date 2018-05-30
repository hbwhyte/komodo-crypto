package komodocrypto.mappers.database;

import org.apache.ibatis.annotations.Select;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.List;

public interface CurrencyPairMapper {

    String GET_CURRENCY_ID = "SELECT `currency_pair_id` FROM `komodoDB`.`currency_pair` WHERE `symbol1` = #{args0} AND `symbol2` = #{args1}";
    String GET_ALL_DATA =   "SELECT * FROM `komodoDB`.`currency_pair`";

    @Select(GET_ALL_DATA)
    public List<CurrencyPair> getAllCurrencyPair();

    @Select(GET_CURRENCY_ID)
    public int getCurrencyPairId(String symbol1, String symbol2);
    
}
