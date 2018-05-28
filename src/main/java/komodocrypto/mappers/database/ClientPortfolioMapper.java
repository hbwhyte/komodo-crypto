package komodocrypto.mappers.database;

import komodocrypto.model.database.ClientPortfolio;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ClientPortfolioMapper {

    String GET_ALL_ENTRIES = "SELECT * FROM `komodoDB`.`client_portfolio`;";

    String INSERT_ENTRY = "INSERT INTO `komodoDB`.`client_portfolio` " +
            "(`user_id`, `deposit_value`, `current_value`, `percentage_ownership`) " +
            "VALUES (#{user_id}, #{deposit_value}, #{current_value}, #{percentage_ownership});";

    String GET_NUM_DEPOSITS_BY_ID = "SELECT COUNT(`deposit_value`) FROM `komodoDB`.`client_portfolio` " +
            "WHERE `user_id` = #{userId};";

    String GET_CURRENT_VALUE = "SELECT `current_value` FROM `komodoDB`.`client_portfolio` " +
            "WHERE `user_id` = #{userId} ORDER BY `timestamp` DESC LIMIT 1;";

    @Select(GET_ALL_ENTRIES)
    public List<ClientPortfolio> getAllEntries();

    @Insert(INSERT_ENTRY)
    public int addEntry(ClientPortfolio clientPortfolio);

    @Select(GET_NUM_DEPOSITS_BY_ID)
    public int getNumDepositsById(int userId);

    @Select(GET_CURRENT_VALUE)
    public BigDecimal getCurrentValue(int userId);
}
