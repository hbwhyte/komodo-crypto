package komodocrypto.model.database;

public class Minutely {

    int minutely_id;
    String timestamp;
    String symbol;
    int exchange_id;
    double open;
    double low;
    double high;
    double close;
    double average;
    int volume;

    public Minutely(int minutely_id, String timestamp, String symbol, int exchange_id, double open, double low, double high, double close, double average, int volume) {
        this.minutely_id = minutely_id;
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.exchange_id = exchange_id;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
        this.average = average;
        this.volume = volume;
    }

    public Minutely() {
    }

    public int getMinutely_id() {
        return minutely_id;
    }

    public void setMinutely_id(int minutely_id) {
        this.minutely_id = minutely_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(int exchange_id) {
        this.exchange_id = exchange_id;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
