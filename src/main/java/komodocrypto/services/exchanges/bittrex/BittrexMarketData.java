package komodocrypto.services.exchanges.bittrex;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataServiceRaw;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.List;

public class BittrexMarketData {


    MarketDataService marketDataService;

    public static void main(String[] args) throws IOException {
        Exchange bittrex = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());

        // Interested in the public market data feed (no authentication)
        MarketDataService marketDataService = bittrex.getMarketDataService();

        generic(marketDataService);
    }

    public BittrexMarketData() {
        this.marketDataService = this.getDefaultMarketDataService();
    }

    public MarketDataService getDefaultMarketDataService(){
        // Use the factory to get Bittrex exchange API using default settings
        Exchange bittrex = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
        return bittrex.getMarketDataService();

    }

    //TODO Why and what kind of info should we get from the previous trades?
//    private void getPreviousTrades(CurrencyPair currencyPair) throws IOException {
//        Trades trades = this.marketDataService.getTrades(currencyPair);
//    }

    public List<LimitOrder> getBids(CurrencyPair currencyPair) throws IOException {
        return this.marketDataService.getOrderBook(currencyPair).getBids();
    }

    public List<LimitOrder> getAsks(CurrencyPair currencyPair) throws IOException {
        return this.marketDataService.getOrderBook(currencyPair).getAsks();
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
