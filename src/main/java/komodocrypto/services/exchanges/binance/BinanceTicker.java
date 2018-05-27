package komodocrypto.services.exchanges.binance;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.*;
import com.binance.api.client.exception.BinanceApiException;
import komodocrypto.configuration.exchange_utils.BinanceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinanceTicker {

    @Autowired
    BinanceUtil binanceUtil;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Get latest ticker price for a given asset pair on Binance
     *
     * @param pair String of asset pair to be traded e.g. BTCLTC, BTCETH, ETHBTC (order matters)
     */
    public TickerPrice getTickerInfo(String pair) {
        // Connect to exchange
        BinanceApiRestClient client = binanceUtil.createExchange();

        // Return latest price for specific pair
        logger.info("Querying latest ticker info for " + pair);
        return client.getPrice(pair);
    }

    /**
     * Get List of latest ticker prices for all asset pairs on Binance
     */
    public List<TickerPrice> getAllTickerInfo() {
        // Connect to exchange
        BinanceApiRestClient client = binanceUtil.createExchange();

        // Return all latest prices
        logger.info("Querying latest ticker info of all assets");
        return client.getAllPrices();
    }

    /**
     * Prints out potentailly relevant trade info
     *
     * @param pair String of asset pair to be traded e.g. BTCLTC, BTCETH, ETHBTC (order matters)
     */
    public void getMarketInfo(String pair) {
        // Connect to exchange
        BinanceApiRestClient client = binanceUtil.createExchange();

        // Getting depth of a symbol
        OrderBook orderBook = client.getOrderBook(pair, 10);
        System.out.println(orderBook.getAsks());

        // Getting latest price of a symbol
        TickerStatistics tickerStatistics = client.get24HrPriceStatistics(pair);
        System.out.println(tickerStatistics);

        // Getting agg trades
        List<AggTrade> aggTrades = client.getAggTrades(pair);
        System.out.println(aggTrades);

        // Weekly candlestick bars for a symbol
        List<Candlestick> candlesticks = client.getCandlestickBars(pair, CandlestickInterval.WEEKLY);
        System.out.println(candlesticks);

        // Getting all book tickers
        List<BookTicker> allBookTickers = client.getBookTickers();
        System.out.println(allBookTickers);

        // Exception handling
        try {
            client.getOrderBook("UNKNOWN", 10);
        } catch (BinanceApiException e) {
            System.out.println(e.getError().getCode()); // -1121
            System.out.println(e.getError().getMsg());  // Invalid symbol
        }
    }
}
