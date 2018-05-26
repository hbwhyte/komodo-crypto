package komodocrypto.controllers;

import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.DepositAddress;
import com.binance.api.client.domain.account.WithdrawResult;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.TickerPrice;
import komodocrypto.exceptions.custom_exceptions.ExchangeConnectionException;
import komodocrypto.model.RootResponse;
import komodocrypto.model.exchanges.BitstampBalance;
import komodocrypto.model.exchanges.KrakenBalance;
import komodocrypto.services.exchanges.binance.BinanceAccount;
import komodocrypto.services.exchanges.binance.BinanceTicker;
import komodocrypto.services.exchanges.binance.BinanceTradeImpl;
import komodocrypto.services.exchanges.bitstamp.BitstampAccount;
import komodocrypto.services.exchanges.bitstamp.BitstampTicker;
import komodocrypto.services.exchanges.bitstamp.BitstampTradeImpl;
import komodocrypto.services.exchanges.kraken.KrakenAccount;
import komodocrypto.services.exchanges.kraken.KrakenTicker;
import komodocrypto.services.exchanges.kraken.KrakenTradeImpl;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class ExchangeController {

    @Autowired
    BinanceAccount binanceAccount;

    @Autowired
    BinanceTicker binanceTicker;

    @Autowired
    BinanceTradeImpl binanceTrade;

    /**
     * Binance: [GET] Return account info
     *
     * @return JSON of Account object
     */
    @GetMapping("/binance/account")
    public Account getBinanceAccountInfo() {
        return binanceAccount.getAccountInfo();
    }

    /**
     * Binance: [GET] Return deposit address for rebalancing
     *
     * @param asset String asset you want to deposit
     * @return JSON of DepositAddress object
     */
    @GetMapping("/binance/deposit")
    public DepositAddress getBinanceDepositInfo(@RequestParam(value = "asset") String asset) {
        return binanceAccount.getDepositAddress(asset);
    }

    /**
     * Binance: [GET] Return latest ticker info for a specific asset pair on Binance
     *
     * @param pair String trading pair, e.g. ETHBTC, LTCBTC etc.
     * @return TickerPrice of the latest prices (JSON)
     */
    @GetMapping("/binance/ticker")
    public TickerPrice getBinanceTickerInfo(@RequestParam(value = "pair") String pair) {
        return binanceTicker.getTickerInfo(pair);
    }

    /**
     * Binance: [GET] Return latest ticker info for all trading pairs on Binance
     *
     * @return List of all the latest prices (JSON)
     */
    @GetMapping("/binance/ticker/")
    public List<TickerPrice> getAllBinanceTickerInfo() {
        return binanceTicker.getAllTickerInfo();
    }

    /**
     * Binance: [POST] Withdraw funds
     *
     * @param asset   String asset symbol, case sensitive (e.g. ETH, BTC, LTC etc.)
     * @param address String wallet address to withdraw money into
     * @param amount  String amount of asset to be withdrawn
     * @return WithdrawResult. If no withdrawl, returns nothing
     */
    @PostMapping("/binance/withdraw")
    public WithdrawResult makeBinanceWithdrawl(@RequestParam(value = "asset") String asset,
                                               @RequestParam(value = "address") String address,
                                               @RequestParam(value = "amount") String amount) {
        return binanceAccount.makeWithdrawl(asset, address, amount);
    }

    /**
     * Binance: [POST] Test market trade
     *
     * @param pair   String asset pair, case & order sensitive (e.g. LTCBTC etc.)
     * @param amount String amount of asset to be traded
     */
    @PostMapping("/binance/tradetest")
    public void makeBinanceTradeTest(@RequestParam(value = "pair") String pair,
                                     @RequestParam(value = "amount") String amount) {
        binanceTrade.testMarketOrder(pair, amount);
    }

    /**
     * Binance: [POST] Live market trade
     *
     * @param pair   String asset pair, case & order sensitive (e.g. ETHBTC, BTCETH, LTCBTC etc.)
     * @param amount String amount of asset to be traded
     */
    @PostMapping("/binance/trade")
    public void makeBinanceTrade(@RequestParam(value = "pair") String pair,
                                 @RequestParam(value = "amount") String amount) {
        binanceTrade.placeMarketOrder(pair, amount);
    }

    /**
     * Binance: [DELETE] Cancel trade order
     *
     * @param pair    String asset pair, case & order sensitive (e.g. ETHBTC, BTCETH, LTCBTC etc.)
     * @param orderId Long id of order to be cancelled
     */
    @DeleteMapping("/binance/trade")
    public void cancelBinanceTrade(@RequestParam(value = "pair") String pair,
                                   @RequestParam(value = "id") Long orderId) {
        binanceTrade.cancelOrder(pair, orderId);
    }

    @GetMapping("/binance/backfill")
    public List<Candlestick> getHistoricalCandlestick(@RequestParam(value = "pair") String pair) {
        return binanceTicker.getHistorical(pair);
    }


    @Autowired
    BitstampAccount bitstampAccount;

    @Autowired
    BitstampTicker bitstampTicker;

    @Autowired
    BitstampTradeImpl bitstampTradeImpl;


    /**
     * Bitstamp: [GET] Account information from Bitstamp
     *
     * @return Account balance for a given asset in Bitstamp
     */
    @GetMapping("/bitstamp/balance")
    public BitstampBalance getBitstampBalance(@RequestParam(value = "asset") Currency asset) throws ExchangeConnectionException {
        return bitstampAccount.getBalance(asset);
    }

    /**
     * Bitstamp: [GET] Trade history from Bitstamp
     *
     * @return AccountInfo object (JSON)
     */
    @GetMapping("/bitstamp/mytrades")
    public List<FundingRecord> getBitstampTradeHistory() throws ExchangeConnectionException {
        return bitstampAccount.getTradeHistory();
    }


    /**
     * Bitstamp: [GET] Return deposit address for rebalancing
     *
     * @param asset Currency asset you want to deposit
     * @return String of deposit address for the given asset
     */
    @GetMapping("/bitstamp/deposit")
    public String getBitstampDepositInfo(@RequestParam(value = "asset") Currency asset)
            throws ExchangeConnectionException {
        return bitstampAccount.getDepositAddress(asset);
    }

    /**
     * Bitstamp: [POST] Withdraw funds
     *
     * @param asset   Currency asset symbol, case sensitive (e.g. ETH, BTC, LTC etc.)
     * @param amount  BigDecimal amount of asset to be withdrawn
     * @param address String wallet address to withdraw money into
     * @return WithdrawResult. If no withdrawl, returns nothing
     */
    @PostMapping("/bitstamp/withdraw")
    public String makeBitstampWithdrawl(@RequestParam(value = "asset") Currency asset,
                                        @RequestParam(value = "address") String address,
                                        @RequestParam(value = "amount") BigDecimal amount)
            throws ExchangeConnectionException {
        return bitstampAccount.makeWithdrawl(asset, amount, address);
    }

    /**
     * Bitstamp: [GET] Return latest ticker info for a specific asset pair on Bitstamp
     *
     * @param pair CurrencyPair asset symbol requested (ETH_BTC, XRP_BTC, LTC_BTC, BCH_BTC ONLY)
     * @return Ticker object of the latest prices (JSON)
     */
    @GetMapping("/bitstamp/ticker")
    public Ticker getBitstampTickerInfo(@RequestParam(value = "pair") CurrencyPair pair)
            throws ExchangeConnectionException {
        return bitstampTicker.getTickerInfo(pair);
    }

    /**
     * Bitstamp: [POST] Place market order
     *
     * @param type   OrderType (BID or ASK ONLY)
     * @param amount BigDecimal amount of asset to be traded
     * @param pair   CurrencyPair asset symbol to be traded (ETH_BTC, XRP_BTC, LTC_BTC, BCH_BTC ONLY)
     * @return String or market order return value (needed to attempt cancellation)
     */
    @PostMapping("/bitstamp/market")
    public String makeBitstampMarketOrder(@RequestParam(value = "type") Order.OrderType type,
                                          @RequestParam(value = "amount") BigDecimal amount,
                                          @RequestParam(value = "pair") CurrencyPair pair)
            throws ExchangeConnectionException {
        return bitstampTradeImpl.placeMarketOrder(type, amount, pair);
    }

    /**
     * Bitstamp: [DELETE] Cancel market order
     *
     * @param marketOrderReturnValue String identifying order to be cancelled,
     *                               returned at time order is placed
     * @return boolean if order was cancelled or not
     */
    @DeleteMapping("/bitstamp/market")
    public Boolean cancelBitstampMarketOrder(@RequestParam(value = "id") String marketOrderReturnValue)
            throws ExchangeConnectionException {
        return bitstampTradeImpl.cancelMarketOrder(marketOrderReturnValue);
    }

    /**
     * Bitstamp: [GET] Get open order list from Bitstamp
     *
     * @return OpenOrders object (JSON)
     */
    @GetMapping("/bitstamp/orders")
    public OpenOrders getBitstampOpenOrders()
            throws ExchangeConnectionException {
        return bitstampTradeImpl.getOpenOrders();
    }

    @Autowired
    KrakenAccount krakenAccount;

    @Autowired
    KrakenTicker krakenTicker;

    @Autowired
    KrakenTradeImpl krakenTradeImpl;


    /**
     * Kraken: [GET] Account information from Kraken
     *
     * @return Account balance for a given asset in Kraken
     */
    @GetMapping("/kraken/balance")
    public KrakenBalance getKrakenBalance(@RequestParam(value = "asset") Currency asset) throws ExchangeConnectionException {
        System.out.println("yo slut");
        return krakenAccount.getBalance(asset);
    }

    /**
     * Kraken: [GET] Trade history from Kraken
     *
     * @return AccountInfo object (JSON)
     */
    @GetMapping("/kraken/mytrades")
    public List<FundingRecord> getKrakenTradeHistory() throws ExchangeConnectionException {
        return krakenAccount.getTradeHistory();
    }


    /**
     * Kraken: [GET] Return deposit address for rebalancing
     *
     * @param asset Currency asset you want to deposit
     * @return String of deposit address for the given asset
     */
    @GetMapping("/kraken/deposit")
    public String getKrakenDepositInfo(@RequestParam(value = "asset") Currency asset)
            throws ExchangeConnectionException {
        return krakenAccount.getDepositAddress(asset);
    }

    /**
     * Kraken: [POST] Withdraw funds
     *
     * @param asset   Currency asset symbol, case sensitive (e.g. ETH, BTC, LTC etc.)
     * @param amount  BigDecimal amount of asset to be withdrawn
     * @param address String wallet address to withdraw money into
     * @return WithdrawResult. If no withdrawl, returns nothing
     */
    @PostMapping("/kraken/withdraw")
    public String makeKrakenWithdrawl(@RequestParam(value = "asset") Currency asset,
                                      @RequestParam(value = "address") String address,
                                      @RequestParam(value = "amount") BigDecimal amount)
            throws ExchangeConnectionException {
        return krakenAccount.makeWithdrawl(asset, amount, address);
    }

    /**
     * Kraken: [GET] Return latest ticker info for a specific asset pair on Kraken
     *
     * @param pair CurrencyPair asset symbol requested (ETH_BTC, XRP_BTC, LTC_BTC, BCH_BTC ONLY)
     * @return Ticker object of the latest prices (JSON)
     */
    @GetMapping("/kraken/ticker")
    public Ticker getKrakenTickerInfo(@RequestParam(value = "pair") CurrencyPair pair)
            throws ExchangeConnectionException {
        return krakenTicker.getTickerInfo(pair);
    }

    /**
     * Kraken: [POST] Place market order
     *
     * @param type   OrderType (BID or ASK ONLY)
     * @param amount BigDecimal amount of asset to be traded
     * @param pair   CurrencyPair asset symbol to be traded (ETH_BTC, XRP_BTC, LTC_BTC, BCH_BTC ONLY)
     * @return String or market order return value (needed to attempt cancellation)
     */
    @PostMapping("/kraken/market")
    public String makeKrakenMarketOrder(@RequestParam(value = "type") Order.OrderType type,
                                        @RequestParam(value = "amount") BigDecimal amount,
                                        @RequestParam(value = "pair") CurrencyPair pair)
            throws ExchangeConnectionException {
        return krakenTradeImpl.placeMarketOrder(type, amount, pair);
    }

    /**
     * Kraken: [DELETE] Cancel market order
     *
     * @param marketOrderReturnValue String identifying order to be cancelled,
     *                               returned at time order is placed
     * @return boolean if order was cancelled or not
     */
    @DeleteMapping("/kraken/market")
    public Boolean cancelKrakenMarketOrder(@RequestParam(value = "id") String marketOrderReturnValue)
            throws ExchangeConnectionException {
        return krakenTradeImpl.cancelMarketOrder(marketOrderReturnValue);
    }

    /**
     * Kraken: [GET] Get open order list from Kraken
     *
     * @return OpenOrders object (JSON)
     */
    @GetMapping("/kraken/orders")
    public OpenOrders getKrakenOpenOrders()
            throws ExchangeConnectionException {
        return krakenTradeImpl.getOpenOrders();
    }
}
