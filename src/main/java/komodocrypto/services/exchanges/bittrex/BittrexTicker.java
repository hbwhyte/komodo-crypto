package komodocrypto.services.exchanges.bittrex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;

public class BittrexTicker {

    MarketDataService marketDataService;

    @Autowired
    BittrexMarketData bittrexMarketData;

    public static void main(String[] args) throws IOException {

        // Use the factory to get Bitstamp exchange API using default settings
        Exchange bittrex = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());

        // Interested in the public market data feed (no authentication required)
        MarketDataService marketDataService = bittrex.getMarketDataService();
        getCurrencyPairTicker(marketDataService, CurrencyPair.ETH_BTC);
    }

    public BittrexTicker() {
        this.marketDataService = bittrexMarketData.getDefaultMarketDataService();
    }

    public Ticker getCurrencyPairTicker(CurrencyPair currencyPair) throws IOException {
        return this.marketDataService.getTicker(currencyPair);
    }

    public BigDecimal getCurrencyPairDailyHigh(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getHigh();
    }

    public BigDecimal getCurrencyPairDailyLow(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getLow();
    }

    public BigDecimal getCurrencyPairDailyOpen(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getOpen();
    }

    public BigDecimal getCurrencyPairLastPrice(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getLast();
    }

    public BigDecimal getCurrencyPairAsk(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getAsk();
    }

    public BigDecimal getCurrencyPairBid(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getBid();
    }

    public BigDecimal getCurrencyPairVwap(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getVwap();
    }

    public BigDecimal getCurrencyPairVolume(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getVolume();
    }


    private static Ticker getCurrencyPairTicker(MarketDataService marketDataService, CurrencyPair currencyPair)
            throws IOException {

        Ticker ticker = marketDataService.getTicker(currencyPair);
        System.out.println(ticker.toString());
        return  ticker;
    }
}
