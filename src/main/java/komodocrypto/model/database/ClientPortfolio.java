package komodocrypto.model.database;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ClientPortfolio {

    int client_portfolio_id;
    int user_id;
    BigDecimal deposit_value;
    BigDecimal current_value;
    double percentage_ownership;
    Timestamp timestamp;

    public int getClient_portfolio_id() {
        return client_portfolio_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public double getPercentage_ownership() {
        return percentage_ownership;
    }

    public void setPercentage_ownership(double percentage_ownership) {
        this.percentage_ownership = percentage_ownership;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
