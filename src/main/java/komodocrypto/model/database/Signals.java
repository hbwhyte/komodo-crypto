package komodocrypto.model.database;

public class Signals {

    int signal_id;
    int exchange_id;
    int minutely_id;
    int hourly_id;
    int daily_id;
    int monthly_id;
    boolean buy;
    boolean sell;
    boolean neutral;

    public Signals(int signal_id, int exchange_id, int minutely_id, int hourly_id, int daily_id, int monthly_id, boolean buy, boolean sell, boolean neutral) {
        this.signal_id = signal_id;
        this.exchange_id = exchange_id;
        this.minutely_id = minutely_id;
        this.hourly_id = hourly_id;
        this.daily_id = daily_id;
        this.monthly_id = monthly_id;
        this.buy = buy;
        this.sell = sell;
        this.neutral = neutral;
    }

    public Signals() {
    }

    public int getSignal_id() {
        return signal_id;
    }

    public void setSignal_id(int signal_id) {
        this.signal_id = signal_id;
    }

    public int getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(int exchange_id) {
        this.exchange_id = exchange_id;
    }

    public int getMinutely_id() {
        return minutely_id;
    }

    public void setMinutely_id(int minutely_id) {
        this.minutely_id = minutely_id;
    }

    public int getHourly_id() {
        return hourly_id;
    }

    public void setHourly_id(int hourly_id) {
        this.hourly_id = hourly_id;
    }

    public int getDaily_id() {
        return daily_id;
    }

    public void setDaily_id(int daily_id) {
        this.daily_id = daily_id;
    }

    public int getMonthly_id() {
        return monthly_id;
    }

    public void setMonthly_id(int monthly_id) {
        this.monthly_id = monthly_id;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public boolean isNeutral() {
        return neutral;
    }

    public void setNeutral(boolean neutral) {
        this.neutral = neutral;
    }
}
