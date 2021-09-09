package komodocrypto.model.exchanges;

import java.math.BigDecimal;

public class KrakenBalance {

    private String currency;
    private BigDecimal total;
    private BigDecimal available;
    private BigDecimal frozen;
    private BigDecimal borrowed;
    private BigDecimal loaned;
    private BigDecimal withdrawing;
    private BigDecimal depositing;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(BigDecimal borrowed) {
        this.borrowed = borrowed;
    }

    public BigDecimal getLoaned() {
        return loaned;
    }

    public void setLoaned(BigDecimal loaned) {
        this.loaned = loaned;
    }

    public BigDecimal getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(BigDecimal withdrawing) {
        this.withdrawing = withdrawing;
    }

    public BigDecimal getDepositing() {
        return depositing;
    }

    public void setDepositing(BigDecimal depositing) {
        this.depositing = depositing;
    }


}
