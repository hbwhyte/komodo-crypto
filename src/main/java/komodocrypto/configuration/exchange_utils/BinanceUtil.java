package komodocrypto.configuration.exchange_utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;

public class BinanceUtil {

  public static Exchange createExchange() {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
    return exchange;
  }
}
