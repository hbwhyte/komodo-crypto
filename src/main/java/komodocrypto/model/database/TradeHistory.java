package komodocrypto.model.database;

public class TradeHistory {

    int trade_id;
    String timestamp;
    int user_id;
    String symbol;
    String buySell;
    int order_id;

    public TradeHistory(int trade_id, String timestamp, int user_id, String symbol, String buySell, int order_id) {
        this.trade_id = trade_id;
        this.timestamp = timestamp;
        this.user_id = user_id;
        this.symbol = symbol;
        this.buySell = buySell;
        this.order_id = order_id;
    }

    public TradeHistory() {
    }

    public int getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(int trade_id) {
        this.trade_id = trade_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBuySell() {
        return buySell;
    }

    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
