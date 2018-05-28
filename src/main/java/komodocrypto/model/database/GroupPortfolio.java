package komodocrypto.model.database;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GroupPortfolio {

    int group_portfolio_id;
    BigDecimal deposit_value;
    BigDecimal current_value;
    int num_investors;
    Timestamp timestamp;

    public int getGroup_portfolio_id() {
        return group_portfolio_id;
    }

    public BigDecimal getDeposit_value() {
        return deposit_value;
    }

    public void setDeposit_value(BigDecimal deposit_value) {
        this.deposit_value = deposit_value;
    }

    public BigDecimal getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(BigDecimal current_value) {
        this.current_value = current_value;
    }

    public int getNum_investors() {
        return num_investors;
    }

    public void setNum_investors(int num_investors) {
        this.num_investors = num_investors;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
