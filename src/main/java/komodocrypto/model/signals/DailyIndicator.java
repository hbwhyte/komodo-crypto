package komodocrypto.model.signals;

public class DailyIndicator {

    private int daily_id;
    private double simpleMovingAverage;
    private double exponentialMovingAverage;

    public int getDaily_id() {
        return daily_id;
    }

    public void setDaily_id(int daily_id) {
        this.daily_id = daily_id;
    }

    public double getSimpleMovingAverage() {
        return simpleMovingAverage;
    }

    public void setSimpleMovingAverage(double simpleMovingAverage) {
        this.simpleMovingAverage = simpleMovingAverage;
    }

    public double getExponentialMovingAverage() {
        return exponentialMovingAverage;
    }

    public void setExponentialMovingAverage(double exponentialMovingAverage) {
        this.exponentialMovingAverage = exponentialMovingAverage;
    }
}
