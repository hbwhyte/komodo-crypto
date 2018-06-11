package komodocrypto.services.arbitrage;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;

public class CoinSpreadTracking {


    @Autowired
    private ArbitrageScanningService scannerService;

    private Exchange[] ETH_BTC;
    private Exchange[] LTC_BTC;
    private Exchange[] BTC_USD;


    public CoinSpreadTracking() {
        this.ETH_BTC = new Exchange[2];
        this.LTC_BTC = new Exchange[2];
        this.BTC_USD = new Exchange[2];
    }







    private boolean validateArbitrageOpportunity(Exchange[] exchanges, CurrencyPair currencyPair) throws IOException {
        BigDecimal bid = exchanges[0].getMarketDataService().getTicker(currencyPair).getBid();
        BigDecimal ask = exchanges[1].getMarketDataService().getTicker(currencyPair).getAsk();
        BigDecimal factor = new BigDecimal(1.1);

        if( bid.compareTo(ask.multiply(factor)) == 1){
            return true;
        } else {
            return false;
        }
    }
}
