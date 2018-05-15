package komodocrypto.services.exchanges.binance;

import komodocrypto.configuration.exchange_utils.BinanceUtil;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.utils.StreamUtils;

import java.io.IOException;

public class BinanceTradeImpl {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BinanceUtil.createExchange();

    generic(exchange);
    raw((BinanceExchange) exchange);
  }

  public static void generic(Exchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.LTC_BTC;
    TradeService tradeService = exchange.getTradeService();

    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(pair));
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }

  public static void raw(BinanceExchange exchange) throws IOException {

    CurrencyPair pair = CurrencyPair.LTC_BTC;
    BinanceTradeService tradeService = (BinanceTradeService) exchange.getTradeService();
    // Get open orders
    OpenOrders orders = tradeService.getOpenOrders(pair);
    LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
    if (order != null) {
      System.out.println(order);
    }
  }
}
