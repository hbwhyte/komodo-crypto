package komodocrypto.mappers.database;

import komodocrypto.model.database.GroupPortfolio;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface GroupPortfolioMapper {

    String GET_ALL_ENTRIES = "SELECT * FROM `komodoDB`.`group_portfolio`;";

    String INSERT_ENTRY = "INSERT INTO `komodoDB`.`group_portfolio` " +
            "(`deposit_value`, `current_value`, `num_investors`) " +
            "VALUES (#{deposit_value}, #{current_value}, #{num_investors})";

    String GET_NUM_INVESTORS = "SELECT `num_investors` FROM `komodoDB`.`group_portfolio` " +
            "ORDER BY `group_portfolio_id` DESC LIMIT 1;";

    String GET_CURRENT_VALUE = "SELECT `current_value` FROM `komodoDB`.`group_portfolio` ORDER BY `timestamp` DESC LIMIT 1;";

    @Select(GET_ALL_ENTRIES)
    public List<GroupPortfolio> getAllEntries();

    @Insert(INSERT_ENTRY)
    public int addEntry(GroupPortfolio groupPortfolio);

    @Select(GET_NUM_INVESTORS)
    public int getNumInvestors();

    @Select(GET_CURRENT_VALUE)
    public BigDecimal getCurrentValue();
}
