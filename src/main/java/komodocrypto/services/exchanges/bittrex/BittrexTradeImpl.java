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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class BittrexTradeImpl {

    TradeService bittrexTradeService;

    public static void main(String[] args) {
        BittrexTradeImpl bittrexTradeImpl = new BittrexTradeImpl();
        bittrexTradeImpl.placeBuyLimitOrder(0.001, CurrencyPair.BTC_USDT, 7000, "1");
        bittrexTradeImpl.placeSellLimitOrder(0.05, CurrencyPair.ETH_BTC, .1, "1");
        bittrexTradeImpl.cancelAllOrders();
    }
    public BittrexTradeImpl() {
        Exchange bittrexExchange = BittrexUtil.createExchange();
        bittrexTradeService = bittrexExchange.getTradeService();
    }

    /** Buying order (the trader is providing the counter currency) */
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

    /** Selling order (the trader is providing the base currency) */
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

    public OpenOrders getOpenOrders(){
        OpenOrders openOrders = null;
        try {
            openOrders = this.bittrexTradeService.getOpenOrders();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openOrders;
    }

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

    public boolean cancelAllOrders(){
        boolean ordersCancelled = false;
        OpenOrders openOrders = this.getOpenOrders();
        for (Order order: openOrders.getAllOpenOrders()) {
            ordersCancelled = cancelOrder(order.getId());
        }
        return ordersCancelled;
    }
}
