package komodocrypto.mappers.database;

import komodocrypto.model.database.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface TransactionMapper {

    public String GET_BALANCE_EXCHANGE_CURRENCY =
            "SELECT `balance_before_transaction` FROM `komodoDB`.`transaction_log` " +
                    "WHERE `exchange_id` = #{args0}, `currency_id` = #{args1} ORDER BY `transaction_id` DESC LIMIT = 1";
    public String INSERT_NEW_TRANSACTION_DATA =
            "INSERT INTO `komodoDB`.`transaction_log`" +
                    "(`exchange_id`, `currency_pair_id`, `transaction_type`, `transaction_amount`, `transaction_fee`, `algorithm`)" +
                    "VALUES (#{exchange_id}, #{currency_pair_id}, #{transaction_type}, #{transaction_amount}, #{transaction_fee}, #{algorithm})";

    @Select(GET_BALANCE_EXCHANGE_CURRENCY)
    BigDecimal getLatestBalance(int exchange_id, int currency_id);

    @Insert(INSERT_NEW_TRANSACTION_DATA)
    public void insertNewData(Transaction tldata);
}