package komodocrypto.services.exchanges.kraken;



import komodocrypto.configuration.exchange_utils.KrakenUtil;
import komodocrypto.exceptions.custom_exceptions.ExchangeConnectionException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KrakenTicker {

    @Autowired
    KrakenUtil krakenUtil;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Get latest ticker price for a given asset pair on Bitstamp
     *
     * @param pair CurrenctPair of asset pair to be traded
     */
    public Ticker getTickerInfo(CurrencyPair pair) throws ExchangeConnectionException {
        // Create Exchange
        Exchange kraken = krakenUtil.createExchange();
        // Connect to account
        MarketDataService marketDataService = kraken.getMarketDataService();

        // Return latest price for specific pair
        logger.info("Querying latest ticker info for " + pair);
        try {
            Ticker ticker = marketDataService.getTicker(pair);
            logger.info("Ticker successfully retrieved.");
            return ticker;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to get ticker information", HttpStatus.BAD_REQUEST);
        }
    }
}

    /**
     * Returns market information of recent trades from the past day.
     *
     * @param pair CurrencyPair requested
     * @return Trades object (JSON)
     * @throws ExchangeConnectionException if unable to connect to the Exchange
     *
    public Trades getKrakenTradesDay(CurrencyPair pair)
            throws ExchangeConnectionException {
        // Create Exchange
        Exchange kraken = krakenUtil.createExchange();
        // Connect to account
        MarketDataService marketDataService = kraken.getMarketDataService();

        // Returns trades for a given pair over the past day
        logger.info("Querying market trade info from the past day for " + pair);
        try {
            Trades trades = marketDataService.getTrades(pair, KrakenMarketDataServiceRaw.KrakenTime.DAY); //BitstampMarketDataServiceRaw.BitstampTime.DAY);
            logger.info("Market data from the past day successfully retrieved.");
            return trades;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to get market trade information", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns market information of recent trades from the past hour.
     *
     * @param pair CurrencyPair requested
     * @return Trades object (JSON)
     * @throws ExchangeConnectionException if unable to connect to the Exchange
     *
    public Trades getKrakenTradesHour(CurrencyPair pair)
            throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        MarketDataService marketDataService = bitstamp.getMarketDataService();

        // Returns trades for a given pair over the past hour
        logger.info("Querying market trade info from the past hour for " + pair);
        try {
            Trades trades = marketDataService.getTrades(pair, BitstampMarketDataServiceRaw.BitstampTime.DAY);
            logger.info("Market data from the past hour successfully retrieved.");
            return trades;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to get market trade information", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns market information of recent trades from the past minute.
     *
     * @param pair CurrencyPair requested
     * @return Trades object (JSON)
     * @throws ExchangeConnectionException if unable to connect to the Exchange
     *
    public Trades getBitstampTradesMinute(CurrencyPair pair)
            throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        MarketDataService marketDataService = bitstamp.getMarketDataService();

        // Returns trades for a given pair over the past minute
        logger.info("Querying market trade info from the past minute for " + pair);
        try {
            Trades trades = marketDataService.getTrades(pair, BitstampMarketDataServiceRaw.BitstampTime.MINUTE);
            logger.info("Market data from the past day successfully retrieved.");
            return trades;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to get market trade information", HttpStatus.BAD_REQUEST);
        }
    }

}
     **/

