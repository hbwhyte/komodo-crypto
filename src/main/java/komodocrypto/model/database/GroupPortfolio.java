package komodocrypto.model.database;

public class GroupPortfolio {

    int group_portfolio_id;
    double deposit_value;
    double current_value;
    int num_investors;

    public GroupPortfolio(int group_portfolio_id, double deposit_value, double current_value, int num_investors) {
        this.group_portfolio_id = group_portfolio_id;
        this.deposit_value = deposit_value;
        this.current_value = current_value;
        this.num_investors = num_investors;
    }

    public GroupPortfolio() {
    }

    public int getGroup_portfolio_id() {
        return group_portfolio_id;
    }

    public void setGroup_portfolio_id(int group_portfolio_id) {
        this.group_portfolio_id = group_portfolio_id;
    }

    public double getDeposit_value() {
        return deposit_value;
    }

    public void setDeposit_value(double deposit_value) {
        this.deposit_value = deposit_value;
    }

    public double getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(double current_value) {
        this.current_value = current_value;
    }

    public int getNum_investors() {
        return num_investors;
    }

    public void setNum_investors(int num_investors) {
        this.num_investors = num_investors;
    }

}
