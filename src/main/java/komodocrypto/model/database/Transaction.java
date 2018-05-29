package komodocrypto.model.database;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {

    private int transaction_id;

    //potentially fk from Exchange
    private int exchange_id;
    //potentially fk from Exchange
    private int currency_id;
    //possible values are inTransfer, outTransfer, sell, buy, deposit, withdrawal
    private String transaction_type;
    private BigDecimal transaction_amount;
    private BigDecimal transaction_fee;
    private BigDecimal balance_before_transaction;
    private BigDecimal balance_after_transaction;
    //determine the method used that made the transaction happen
    private String algorithm;
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

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
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

    public BigDecimal getBalance_before_transaction() {
        return balance_before_transaction;
    }

    public void setBalance_before_transaction(BigDecimal balance_before_transaction) {
        this.balance_before_transaction = balance_before_transaction;
    }

    public BigDecimal getBalance_after_transaction() {
        return balance_after_transaction;
    }

    public void setBalance_after_transaction(BigDecimal balance_after_transaction) {
        this.balance_after_transaction = balance_after_transaction;
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
