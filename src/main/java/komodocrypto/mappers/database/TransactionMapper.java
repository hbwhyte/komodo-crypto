package komodocrypto.mappers.database;

import komodocrypto.model.database.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface TransactionMapper {

    String SELECT_BALANCE_EXCHANGE_CURRENCY =
            "SELECT `balance_after_transaction` FROM `komodoDB`.`transaction_log` " +
                    "WHERE `exchange_id` = #{args0}, `currency_id` = #{args1} ORDER BY `transaction_id` DESC LIMIT = 1";

    String INSERT_TRANSACTION = "INSERT INTO `komodoDB`.`transaction_log` " +
            "(`exchange_id`, `currency_pair_id`, `transaction_type`, `transaction_amount`, `transaction_fee`, " +
            "`balance_before_transaction`, `algorithm`) VALUES " +
            "(#{exchange_id}, #{currency_pair_id}, #{transaction_type}, #{transaction_amount}, #{transaction_fee}, " +
            "#{balance_before_transaction}, #{algorithm});";

    @Select(SELECT_BALANCE_EXCHANGE_CURRENCY)
    public BigDecimal getBalanceBeforeTransaction(int exchange_id, int currency_id);

    @Insert(INSERT_TRANSACTION)
    public int addTransaction(Transaction transaction);
}