package komodocrypto.services.exchanges.bitstamp;

import komodocrypto.configuration.exchange_utils.BitstampUtil;
import komodocrypto.exceptions.custom_exceptions.ExchangeConnectionException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.service.BitstampTradeService;
import org.knowm.xchange.bitstamp.service.BitstampTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Connect to Bitstamp exchange with authentication
 * Enter, review and cancel limit orders
 */
@Service
public class BitstampTradeImpl {

    @Autowired
    BitstampUtil bitstampUtil;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String placeMarketOrder(Order.OrderType type, BigDecimal amount, CurrencyPair pair)
            throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        TradeService tradeService = bitstamp.getTradeService();

        // Place market order
        logger.info("Attempting market order...");
        MarketOrder marketOrder =
                new MarketOrder(type, amount, pair);
        try {
            String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
            logger.info("Order successfully placed.");
            return marketOrderReturnValue;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to place order", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean cancelMarketOrder(String marketOrderReturnValue) throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        TradeService tradeService = bitstamp.getTradeService();

        // Cancel the added order
        logger.info("Attempting to cancel order...");
        try {
            boolean cancelResult = tradeService.cancelOrder(marketOrderReturnValue);
            logger.info("Order successfully cancelled.");
            return cancelResult;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to cancel order", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Generates list of my open orders
     *
     * @return Object containing my open and/or hidden orders
     * @throws ExchangeConnectionException if unable to connect to exchange
     */
    public OpenOrders getOpenOrders() throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        TradeService tradeService = bitstamp.getTradeService();

        // Get open orders
        logger.info("Requesting open Bitstamp orders...");
        try {
            OpenOrders openOrders = tradeService.getOpenOrders();
            logger.info("Orders successfully retrieved.");
            return openOrders;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to retrieve orders", HttpStatus.BAD_REQUEST);
        }
    }
}
