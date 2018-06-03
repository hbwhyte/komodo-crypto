package komodocrypto.model.arbitrage;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

public class ArbitrageOutput {

    private Exchange exchangeHigh;
    private Exchange exchangeLow;
    private CurrencyPair currencyPair;
    private BigDecimal amount;
    private String orderIdHigh; // orderId from first exchange
    private String orderIdLow; // orderId from second exchange
    private long timestampHigh;
    private long timestampLow;
    private BigDecimal profit;

    public Exchange getExchangeHigh() {
        return exchangeHigh;
    }

    public void setExchangeHigh(Exchange exchangeHigh) {
        this.exchangeHigh = exchangeHigh;
    }

    public Exchange getExchangeLow() {
        return exchangeLow;
    }

    public void setExchangeLow(Exchange exchangeLow) {
        this.exchangeLow = exchangeLow;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderIdHigh() {
        return orderIdHigh;
    }

    public void setOrderIdHigh(String orderIdHigh) {
        this.orderIdHigh = orderIdHigh;
    }

    public String getOrderIdLow() {
        return orderIdLow;
    }

    public void setOrderIdLow(String orderIdLow) {
        this.orderIdLow = orderIdLow;
    }

    public long getTimestampHigh() {
        return timestampHigh;
    }

    public void setTimestampHigh(long timestampHigh) {
        this.timestampHigh = timestampHigh;
    }

    public long getTimestampLow() {
        return timestampLow;
    }

    public void setTimestampLow(long timestampLow) {
        this.timestampLow = timestampLow;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
