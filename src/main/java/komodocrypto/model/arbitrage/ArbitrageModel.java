package komodocrypto.model.arbitrage;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ArbitrageModel {

    private int id;
    private Timestamp timestamp;
    private String currencyPair;
    private BigDecimal difference;
    private BigDecimal lowAsk;
    private BigDecimal lowAskExchange;
    private BigDecimal highBid;
    private BigDecimal highBidExchange;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public BigDecimal getLowAsk() {
        return lowAsk;
    }

    public void setLowAsk(BigDecimal lowAsk) {
        this.lowAsk = lowAsk;
    }

    public BigDecimal getLowAskExchange() {
        return lowAskExchange;
    }

    public void setLowAskExchange(BigDecimal lowAskExchange) {
        this.lowAskExchange = lowAskExchange;
    }

    public BigDecimal getHighBid() {
        return highBid;
    }

    public void setHighBid(BigDecimal highBid) {
        this.highBid = highBid;
    }

    public BigDecimal getHighBidExchange() {
        return highBidExchange;
    }

    public void setHighBidExchange(BigDecimal highBidExchange) {
        this.highBidExchange = highBidExchange;
    }
}
