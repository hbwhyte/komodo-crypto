package komodocrypto.model.database;

public class Portfolio {

    int portfolio_id;
    int user_id;
    int trade_id;
    String symbol;
    double amount;
    double gainLoss;
    double numberOfShares;

    public Portfolio(int portfolio_id, int user_id, int trade_id, String symbol, double amount, double gainLoss, double numberOfShares) {
        this.portfolio_id = portfolio_id;
        this.user_id = user_id;
        this.trade_id = trade_id;
        this.symbol = symbol;
        this.amount = amount;
        this.gainLoss = gainLoss;
        this.numberOfShares = numberOfShares;
    }

    public Portfolio() {
    }

    public int getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(int trade_id) {
        this.trade_id = trade_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getGainLoss() {
        return gainLoss;
    }

    public void setGainLoss(double gainLoss) {
        this.gainLoss = gainLoss;
    }

    public double getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(double numberOfShares) {
        this.numberOfShares = numberOfShares;
    }
}
