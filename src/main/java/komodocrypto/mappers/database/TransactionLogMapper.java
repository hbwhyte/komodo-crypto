package komodocrypto.mappers.database;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface TransactionLogMapper {

    public String GET_BALANCE_EXCHANGE_CURRENCY =
            "SELECT `balance_after_transaction` FROM `komodoDB`.`transaction_log` " +
                    "WHERE `exchange_id` = #{args0}, `currency_id` = #{args1} ORDER BY `transaction_id` DESC LIMIT = 1";

    @Select(GET_BALANCE_EXCHANGE_CURRENCY)
    BigDecimal getBalanceBeforeTransaction(int exchange_id, int currency_id);

}
