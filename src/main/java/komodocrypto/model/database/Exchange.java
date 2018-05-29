package komodocrypto.model.database;

import java.math.BigDecimal;

public class Exchange {

    int exchange_id;
    String exchange_name;
    BigDecimal transfer_fee;
    BigDecimal buy_fee;
    BigDecimal sell_fee;

    public Exchange(int exchange_id, String exchange_name, BigDecimal transfer_fee, BigDecimal buy_fee, BigDecimal sell_fee) {
        this.exchange_id = exchange_id;
        this.exchange_name = exchange_name;
        this.transfer_fee = transfer_fee;
        this.buy_fee = buy_fee;
        this.sell_fee = sell_fee;
    }

    public Exchange() {
    }

    public int getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(int exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getExchange_name() {
        return exchange_name;
    }

    public void setExchange_name(String exchange_name) {
        this.exchange_name = exchange_name;
    }

    public BigDecimal getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(BigDecimal transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    public BigDecimal getBuy_fee() {
        return buy_fee;
    }

    public void setBuy_fee(BigDecimal buy_fee) {
        this.buy_fee = buy_fee;
    }

    public BigDecimal getSell_fee() {
        return sell_fee;
    }

    public void setSell_fee(BigDecimal sell_fee) {
        this.sell_fee = sell_fee;
    }
}
