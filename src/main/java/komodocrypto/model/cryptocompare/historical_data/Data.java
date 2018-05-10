package komodocrypto.model.cryptocompare.historical_data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Data {

    int id;
    int time;
    String fromCurrency;
    String toCurrency;
    String exchange;
    BigDecimal close;
    BigDecimal high;
    BigDecimal low;
    BigDecimal open;
    BigDecimal average;
    @JsonProperty("volumefrom")
    BigDecimal volumeFrom;
    @JsonProperty("volumeto")
    BigDecimal volumeTo;

    // There is no setter for id as it is auto-incremented in the MySQL database.
    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = BigDecimal.valueOf(close);
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = BigDecimal.valueOf(high);
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = BigDecimal.valueOf(low);
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = BigDecimal.valueOf(open);
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public BigDecimal getVolumeFrom() {
        return volumeFrom;
    }

    public void setVolumeFrom(double volumeFrom) {
        this.volumeFrom = BigDecimal.valueOf(volumeFrom);
    }

    public BigDecimal getVolumeTo() {
        return volumeTo;
    }

    public void setVolumeTo(double volumeTo) {
        this.volumeTo = BigDecimal.valueOf(volumeTo);
    }
}
