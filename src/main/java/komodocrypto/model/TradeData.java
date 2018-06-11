package komodocrypto.model;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;
import java.sql.Timestamp;

// Contains most of the objects and data needed to make a trade and persist data regarding it. Simplifies and ideally
// speeds up the application.
public class TradeData {

    // Exchange data
    Exchange fromExchange;
    Exchange toExchange;
    int idFromExchange;
    int idToExchange;
    BigDecimal balanceFromExchange;
    BigDecimal balanceToExchange;

    // Currency data
    CurrencyPair currencyPair;
    int idFromCurrency;
    int idToCurrency;
    int idCurrencyPair;

    public Exchange getFromExchange() {
        return fromExchange;
    }

    public void setFromExchange(Exchange fromExchange) {
        this.fromExchange = fromExchange;
    }

    public Exchange getToExchange() {
        return toExchange;
    }

    public void setToExchange(Exchange toExchange) {
        this.toExchange = toExchange;
    }

    public int getIdFromExchange() {
        return idFromExchange;
    }

    public void setIdFromExchange(int idFromExchange) {
        this.idFromExchange = idFromExchange;
    }

    public int getIdToExchange() {
        return idToExchange;
    }

    public void setIdToExchange(int idToExchange) {
        this.idToExchange = idToExchange;
    }

    public BigDecimal getBalanceFromExchange() {
        return balanceFromExchange;
    }

    public void setBalanceFromExchange(BigDecimal balanceFromExchange) {
        this.balanceFromExchange = balanceFromExchange;
    }

    public BigDecimal getBalanceToExchange() {
        return balanceToExchange;
    }

    public void setBalanceToExchange(BigDecimal balanceToExchange) {
        this.balanceToExchange = balanceToExchange;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public int getIdFromCurrency() {
        return idFromCurrency;
    }

    public void setIdFromCurrency(int idFromCurrency) {
        this.idFromCurrency = idFromCurrency;
    }

    public int getIdToCurrency() {
        return idToCurrency;
    }

    public void setIdToCurrency(int idToCurrency) {
        this.idToCurrency = idToCurrency;
    }

    public int getIdCurrencyPair() {
        return idCurrencyPair;
    }

    public void setIdCurrencyPair(int idCurrencyPair) {
        this.idCurrencyPair = idCurrencyPair;
    }
}
