package komodocrypto.model.database;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {

    private int transaction_id;
    private int exchange_id; //potentially fk from Exchange
    private int currency_pair_id; //fk from currency_pair
    private String transaction_type; //possible values are inTransfer, outTransfer, sell, buy, deposit, withdrawal
    private BigDecimal transaction_amount;
    private BigDecimal transaction_fee;
    private BigDecimal balance_currency1;
    private BigDecimal balance_currency2;
    private String algorithm; //determine the method used that made the transaction happen
    private Timestamp timestamp;

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(int exchange_id) {
        this.exchange_id = exchange_id;
    }

    public int getCurrency_pair_id() {
        return currency_pair_id;
    }

    public void setCurrency_pair_id(int currency_pair_id) {
        this.currency_pair_id = currency_pair_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public BigDecimal getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(BigDecimal transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public BigDecimal getTransaction_fee() {
        return transaction_fee;
    }

    public void setTransaction_fee(BigDecimal transaction_fee) {
        this.transaction_fee = transaction_fee;
    }

    public BigDecimal getBalance_currency1() {
        return balance_currency1;
    }

    public void setBalance_currency1(BigDecimal balance_currency1) {
        this.balance_currency1 = balance_currency1;
    }

    public BigDecimal getBalance_currency2() {
        return balance_currency2;
    }

    public void setBalance_currency2(BigDecimal balance_currency2) {
        this.balance_currency2 = balance_currency2;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
