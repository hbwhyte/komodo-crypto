package komodocrypto.services.exchanges.bittrex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;


@Service
public class BittrexTicker {

    MarketDataService marketDataService;

    @Autowired
    BittrexMarketData bittrexMarketData;

    public static void main(String[] args) throws IOException {

        // Use the factory to get Bitstamp exchange API using default settings
        Exchange bittrex = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());

        // Interested in the public market data feed (no authentication required)
        MarketDataService marketDataService = bittrex.getMarketDataService();
        BittrexTicker bittrexTicker = new BittrexTicker();
        bittrexTicker.getCurrencyPairTicker(CurrencyPair.ETH_BTC);
    }

    public BittrexTicker() {
        this.marketDataService = bittrexMarketData.getDefaultMarketDataService();
    }

    /**
     * Returns a Ticker obj with the latest info from a currency pair.
     * @param currencyPair the
     * @return
     * @throws IOException
     */

    public Ticker getCurrencyPairTicker(CurrencyPair currencyPair) throws IOException {
        return this.marketDataService.getTicker(currencyPair);
    }

    /**
     * Get the highest price of the day for a given currency pair
     * @param currencyPair
     * @return the high of the day as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairDailyHigh(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getHigh();
    }

    /**
     * Get the lowest price of the day for a given currency pair
     * @param currencyPair
     * @return the low of the day as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairDailyLow(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getLow();
    }

    /**
     * Get the open price of the day for a given currency pair
     * @param currencyPair
     * @return the open of the day as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairDailyOpen(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getOpen();
    }

    /**
     * Get the last price of the day for a given currency pair
     * @param currencyPair
     * @return the last price of the day as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairLastPrice(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getLast();
    }

    /**
     * Get the current nearest Ask for a given currency pair
     * @param currencyPair
     * @return the current ask as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairAsk(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getAsk();
    }

    /**
     * Get the current nearest Bid for a given currency pair
     * @param currencyPair
     * @return the current bid as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairBid(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getBid();
    }

    /**
     * Get the current Volume Weighted Average Price(VWAP) for a given currency pair
     * @param currencyPair
     * @return the current VWAP as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairVwap(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getVwap();
    }

    /**
     * Get the current daily Volume for a given currency pair
     * @param currencyPair
     * @return the current Volume as a BigDecimal
     * @throws IOException
     */
    public BigDecimal getCurrencyPairVolume(CurrencyPair currencyPair) throws IOException{
        return this.marketDataService.getTicker(currencyPair).getVolume();
    }

}
