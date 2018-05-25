package komodocrypto.mappers;

import komodocrypto.model.database.GroupPortfolio;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupPortfolioMapper {

    String fieldsValuesGroupPortfolio =
            "(`deposit_value`, `current_value`, `num_investors`) " +
                    "VALUES (#{deposit_value}, #{current_value}, #{num_investors});";


    String INSERT_NEW_USER_TO_POOL = "INSERT IGNORE INTO `komodoDB`.`group_portfolio` " + fieldsValuesGroupPortfolio;

    String SELECT_NUM_INVESTOR = "SELECT `num_investor` FROM `komodoDB`.`group_portfolio` ORDER BY `group_portfolio_id` DESC LIMIT 1";

    @Insert(INSERT_NEW_USER_TO_POOL)
    public void insertNewUserToPool(GroupPortfolio groupPortfolio);

    @Select(SELECT_NUM_INVESTOR)
    public int selectNumInvestor();



}
