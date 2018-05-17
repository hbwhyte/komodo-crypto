package komodocrypto.services.exchanges.bittrex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.ArrayList;

public class BittrexTickerData {
    public static void main(String[] args) throws IOException {

        // Use the factory to get Bitstamp exchange API using default settings
        Exchange bittrex = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());

        // Interested in the public market data feed (no authentication required)
        MarketDataService marketDataService = bittrex.getMarketDataService();
        getCurrencyPairTicker(marketDataService, CurrencyPair.ETH_BTC);
    }

    private static Ticker getCurrencyPairTicker(MarketDataService marketDataService, CurrencyPair currencyPair)
            throws IOException {

        Ticker ticker = marketDataService.getTicker(currencyPair);
        System.out.println(ticker.toString());
        return  ticker;
    }
}
