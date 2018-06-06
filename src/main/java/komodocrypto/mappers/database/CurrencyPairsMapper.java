package komodocrypto.mappers.database;

import komodocrypto.model.database.CurrencyPairs;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CurrencyPairsMapper {

    String GET_CURRENCY_ID = "SELECT `currency_pair_id` FROM `komodoDB`.`currency_pairs` WHERE `symbol1` = #{args0} AND `symbol2` = #{args1};";
    String GET_ALL_DATA =   "SELECT * FROM `komodoDB`.`currency_pairs`;";

    @Select(GET_ALL_DATA)
    @Results(@Result(property = "currencyPairId", column = "currency_pair_id"))
    public List<CurrencyPairs> getAllCurrencyPairs();

    @Select(GET_CURRENCY_ID)
    public int getCurrencyPairId(String symbol1, String symbol2);
    
}
