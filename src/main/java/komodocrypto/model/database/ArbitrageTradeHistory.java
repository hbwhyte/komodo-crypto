package komodocrypto.model.database;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ArbitrageTradeHistory{

    int arbitrage_id;
    int currency_pair_id;
    int buy_transaction_id;
    int sell_transaction_id;
    BigDecimal sell_price;
    BigDecimal sell_amount;
    BigDecimal buy_price;
    BigDecimal buy_amount;
    String status;
    int buy_exchange_id;
    int sell_exchange_id;
    Timestamp timestamp;

    public int getArbitrage_id() {
        return arbitrage_id;
    }

    public void setArbitrage_id(int arbitrage_id) {
        this.arbitrage_id = arbitrage_id;
    }

    public int getCurrency_pair_id() {
        return currency_pair_id;
    }

    public void setCurrency_pair_id(int currency_pair_id) {
        this.currency_pair_id = currency_pair_id;
    }

    public int getBuy_transaction_id() {
        return buy_transaction_id;
    }

    public void setBuy_transaction_id(int buy_transaction_id) {
        this.buy_transaction_id = buy_transaction_id;
    }

    public int getSell_transaction_id() {
        return sell_transaction_id;
    }

    public void setSell_transaction_id(int sell_transaction_id) {
        this.sell_transaction_id = sell_transaction_id;
    }

    public BigDecimal getSell_price() {
        return sell_price;
    }

    public void setSell_price(BigDecimal sell_price) {
        this.sell_price = sell_price;
    }

    public BigDecimal getSell_amount() {
        return sell_amount;
    }

    public void setSell_amount(BigDecimal sell_amount) {
        this.sell_amount = sell_amount;
    }

    public BigDecimal getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(BigDecimal buy_price) {
        this.buy_price = buy_price;
    }

    public BigDecimal getBuy_amount() {
        return buy_amount;
    }

    public void setBuy_amount(BigDecimal buy_amount) {
        this.buy_amount = buy_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBuy_exchange_id() {
        return buy_exchange_id;
    }

    public void setBuy_exchange_id(int buy_exchange_id) {
        this.buy_exchange_id = buy_exchange_id;
    }

    public int getSell_exchange_id() {
        return sell_exchange_id;
    }

    public void setSell_exchange_id(int sell_exchange_id) {
        this.sell_exchange_id = sell_exchange_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
