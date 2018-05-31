package komodocrypto.model.signals;

public class Signal {

    private String description;
    private long time;
    private String fromCurrency;
    private String toCurrency;
    private float sell_neutral_buy;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public float getSell_neutral_buy() {
        return sell_neutral_buy;
    }

    public void setSell_neutral_buy(float sell_neutral_buy) {
        this.sell_neutral_buy = sell_neutral_buy;
    }
}
