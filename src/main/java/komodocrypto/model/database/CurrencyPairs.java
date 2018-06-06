package komodocrypto.model.database;

public class CurrencyPairs {

    int currencyPairId;
    String symbol1;
    String symbol2;

    public int getCurrencyPairId() {
        return currencyPairId;
    }

    public String getSymbol1() {
        return symbol1;
    }

    public void setSymbol1(String symbol1) {
        this.symbol1 = symbol1;
    }

    public String getSymbol2() {
        return symbol2;
    }

    public void setSymbol2(String symbol2) {
        this.symbol2 = symbol2;
    }
}
