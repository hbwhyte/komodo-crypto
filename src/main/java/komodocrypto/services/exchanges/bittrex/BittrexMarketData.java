package komodocrypto.services.exchanges.bittrex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataServiceRaw;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class BittrexMarketData {

    public static void main(String[] args) throws IOException {

        // Use the factory to get Bitstamp exchange API using default settings
        Exchange bittrex = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());

        // Interested in the public market data feed (no authentication)
        MarketDataService marketDataService = bittrex.getMarketDataService();

        generic(marketDataService);
    }
    // Get the latest trade data for LTC/BTC
    private static void generic(MarketDataService marketDataService) throws IOException {

        // Number of LTC/BTC trades in the past day
        Trades trades =
                marketDataService.getTrades(
                        CurrencyPair.LTC_BTC, BitstampMarketDataServiceRaw.BitstampTime.DAY);
        System.out.println("Trades, day= " + trades.getTrades().size());

        // Number of LTC/BTC trades in the past hour
        trades = marketDataService.getTrades(
                CurrencyPair.LTC_BTC, BitstampMarketDataServiceRaw.BitstampTime.HOUR);
        System.out.println("Trades, hour= " + trades.getTrades().size());
        // Print out all of those trades in the past hour
        System.out.println(trades.toString());

        // Number of LTC/BTC trades in the past minute
        trades = marketDataService.getTrades(
                CurrencyPair.LTC_BTC, BitstampMarketDataServiceRaw.BitstampTime.MINUTE);
        System.out.println("Trades, minute= " + trades.getTrades().size());
    }
}
