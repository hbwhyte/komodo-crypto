package komodocrypto.model.arbitrage;


import java.math.BigDecimal;
import java.util.List;

public class ArbitrageOutput {

    private String exchangeHigh;
    private String exchangeLow;
    private String currencyPair;
    private BigDecimal amount;
    private List<TradeDetails> tradeDetails;

    public String getExchangeHigh() {
        return exchangeHigh;
    }

    public void setExchangeHigh(String exchangeHigh) {
        this.exchangeHigh = exchangeHigh;
    }

    public String getExchangeLow() {
        return exchangeLow;
    }

    public void setExchangeLow(String exchangeLow) {
        this.exchangeLow = exchangeLow;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<TradeDetails> getTradeDetails() {
        return tradeDetails;
    }

    public void setTradeDetails(List<TradeDetails> tradeDetails) {
        this.tradeDetails = tradeDetails;
    }
}
