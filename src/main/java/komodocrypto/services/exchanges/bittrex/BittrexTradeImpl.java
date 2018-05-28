package komodocrypto.services.exchanges.bittrex;

import komodocrypto.configuration.exchange_utils.BittrexUtil;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.Bittrex;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.dto.trade.BittrexLimitOrder;
import org.knowm.xchange.bittrex.service.BittrexTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class BittrexTradeImpl {

    TradeService bittrexTradeService;

    public static void main(String[] args) {
        BittrexTradeImpl bittrexTradeImpl = new BittrexTradeImpl();
        bittrexTradeImpl.placeBuyLimitOrder(0.001, CurrencyPair.BTC_USDT, 7000, "1");
        bittrexTradeImpl.placeSellLimitOrder(0.05, CurrencyPair.ETH_BTC, .1, "1");
        bittrexTradeImpl.cancelAllOrders();
    }
    public BittrexTradeImpl() {
        BittrexUtil bittrexUtil = new BittrexUtil();
        Exchange bittrexExchange = bittrexUtil.createExchange();
        bittrexTradeService = bittrexExchange.getTradeService();
    }


    /**
     * Buying order (the trader is providing the counter currency)
     * Places a limit order on the Bittrex exchange.
     * @param originalAmount the amount of currency to be bought
     * @param currencyPair the currency pair
     * @param limitPrice the price at which the order is intended to be filled
     * @param id an id for the transaction (So far it seems it has importance,
     *           as the exchange ends up assigning the order its own id)
     * @return the order ID if the order was placed, null otherwise
     */
    public String placeBuyLimitOrder(double originalAmount, CurrencyPair currencyPair, double limitPrice,
                                     String id){
        String orderID = null;
        BigDecimal amount = new BigDecimal(originalAmount);
        BigDecimal price = new BigDecimal(limitPrice);
        Date timestamp = new Date();
        LimitOrder limitOrder = new LimitOrder( Order.OrderType.BID, amount, currencyPair, id,
                                                timestamp, price);

        BittrexLimitOrder bittrexLimitOrder;
        try {
            orderID = this.bittrexTradeService.placeLimitOrder(limitOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderID;
    }


    /**
     * Selling order (the trader is providing the base currency)
     * Places a limit order on the Bittrex exchange.
     * @param originalAmount the amount of currency to be bought
     * @param currencyPair the currency pair
     * @param limitPrice the price at which the order is intended to be filled
     * @param id an id for the transaction (So far it seems it has importance,
     *           as the exchange ends up assigning the order its own id)
     * @return the order ID if the order was placed, null otherwise
     */
    public String placeSellLimitOrder(double originalAmount, CurrencyPair currencyPair, double limitPrice,
                                      String id){
        String orderID = null;
        BigDecimal amount = new BigDecimal(originalAmount);
        BigDecimal price = new BigDecimal(limitPrice);
        Date timestamp = new Date();
        LimitOrder limitOrder = new LimitOrder( Order.OrderType.ASK, amount, currencyPair, id,
                timestamp, price);
        try {
            orderID = this.bittrexTradeService.placeLimitOrder(limitOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderID;
    }

    /**
     * Returns an OpenOrders obj that contains a list of the open orders
     * @return an OpenOrders obj
     */
    public OpenOrders getOpenOrders(){
        OpenOrders openOrders = null;
        try {
            openOrders = this.bittrexTradeService.getOpenOrders();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openOrders;
    }

    /**
     * Cancels an order with the given id
     * @param orderId the id of the order given by the exchange
     * @return true if the order was cancelled, false if not
     */
    public boolean cancelOrder(String orderId){
        boolean orderStatus = false;
        try {
            orderStatus =this. bittrexTradeService.cancelOrder(orderId);
            System.out.println("Order " + orderId + " was cancelled");
        } catch (IOException e) {
            System.out.println("IOException while cancelling order " + orderId);
            e.printStackTrace();
        }
        return orderStatus;
    }

    /**
     * Cancels all the open orders
     * @return true if the orders were cancelled, false if not
     */
    public boolean cancelAllOrders(){
        boolean ordersCancelled = false;
        OpenOrders openOrders = this.getOpenOrders();
        for (Order order: openOrders.getAllOpenOrders()) {
            ordersCancelled = cancelOrder(order.getId());
        }
        return ordersCancelled;
    }
}
