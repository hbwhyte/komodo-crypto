package komodocrypto.model.exchanges.bitstamp;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class Bitstamp {

        public static void main(String[] args) throws IOException {

            // Use the factory to get Bitstamp exchange API using default settings
            Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

            // Interested in the public market data feed (no authentication required)
            MarketDataService marketDataService = bitstamp.getMarketDataService();

            genericTicker(marketDataService);
        }

        private static void genericTicker(MarketDataService marketDataService) throws IOException {

            // Currently there is an issue that Xchange is missing the open data. Heather is looking into it
            Ticker ticker = marketDataService.getTicker(CurrencyPair.LTC_BTC);

            System.out.println(ticker.toString());
        }
}
