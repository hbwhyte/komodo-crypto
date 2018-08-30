package komodocrypto.services.exchanges.kraken;//package komodocrypto.services.exchanges.kraken.kraken;
//
//import java.io.IOException;
//
//import komodocrypto.configuration.exchange_utils.BitstampUtil;
//import org.knowm.xchange.Exchange;
//import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
//import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams;
//import org.knowm.xchange.bitstamp.service.BitstampTradeServiceRaw;
//import org.knowm.xchange.currency.CurrencyPair;
//import org.knowm.xchange.dto.marketdata.Trades;
//import org.knowm.xchange.service.trade.TradeService;
//import org.springframework.stereotype.Service;
//
///**
// * Example showing the following:
// *
// * <ul>
// *   <li>Connect to Bitstamp exchange with authentication
// *   <li>get user trade history
// * </ul>
// */
//@Service
//public class KrakenTradeHistory {
//
//    public static void main(String[] args) throws IOException {
//
//        Exchange kraken = kraken.createExchange();
//        TradeService tradeService = bitstamp.getTradeService();
//
//        generic(tradeService);
//    }
//
//    private static void generic(TradeService tradeService) throws IOException {
//
//        // If you have API key, connects to users Bitstamp exchange account
//        Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
//        System.out.println(trades);
//
//        // Warning: using a limit here can be misleading. The underlying call
//        // retrieves trades, withdrawals, and deposits. So the example here will
//        // limit the result to 1 of those types and from those 10 only trades are
//        // returned. It is recommended to use the raw service demonstrated below
//        // if you want to use this feature.
//        BitstampTradeHistoryParams params =
//                (BitstampTradeHistoryParams) tradeService.createTradeHistoryParams();
//        params.setPageLength(10);
//        params.setCurrencyPair(CurrencyPair.LTC_BTC);
//        Trades tradesLimitedTo10 = tradeService.getTradeHistory(params);
//        System.out.println(tradesLimitedTo10);
//    }
//
//    private static void raw(BitstampTradeServiceRaw tradeService) throws IOException {
//
//        // If you have API key, connects to users Bitstamp exchange account
//        BitstampUserTransaction[] tradesLimitedTo10 =
//                tradeService.getBitstampUserTransactions(10L, CurrencyPair.LTC_BTC);
//        for (BitstampUserTransaction trade : tradesLimitedTo10) {
//            System.out.println(trade);
//        }
//    }
//}
