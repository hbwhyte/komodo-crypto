package komodocrypto.controllers;

import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.WithdrawResult;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.TickerPrice;
import komodocrypto.model.RootResponse;
import komodocrypto.services.exchanges.binance.BinanceAccount;
import komodocrypto.services.exchanges.binance.BinanceTicker;
import komodocrypto.services.exchanges.binance.BinanceTradeImpl;
import komodocrypto.services.exchanges.bitstamp.BitstampAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.util.List;

@RestController
public class ExchangeController {

    @Autowired
    BitstampAccount bitstampAccount;

    @Autowired
    BinanceAccount binanceAccount;

    @Autowired
    BinanceTicker binanceTicker;

    @Autowired
    BinanceTradeImpl binanceTrade;

    @GetMapping("/bitstamp")
    public void getBitstampAccountInfo() throws IOException {
        bitstampAccount.accountInfo();
    }

    /**
     * Binance: [GET] Return account info
     * @return
     */
    @GetMapping("/binance/account")
    public Account getBinanceAccountInfo() {
        return binanceAccount.getAccountInfo();
    }

    /**
     * Binance: [GET] Return latest ticker info for a specific asset pair on Binance
     *
     * @param pair String trading pair, e.g. ETHBTC, LTCBTC etc.
     * @return TickerPrice of the latest prices (JSON)
     */
    @GetMapping("/binance/ticker")
    public TickerPrice getBinanceTickerInfo(@QueryParam(value = "pair") String pair) {
        return binanceTicker.getTickerInfo(pair);
    }

    /**
     * Binance: [GET] Return latest ticker info for all trading pairs on Binance
     *
     * @return List of all the latest prices (JSON)
     */
    @GetMapping("/binance/ticker/")
    public List<TickerPrice> getBinanceTickerInfo() {
        return binanceTicker.getAllTickerInfo();
    }

    /**
     * Binance: [POST] Withdraw funds
     *
     * @param asset String asset symbol, case sensitive (e.g. ETH, BTC, LTC etc.)
     * @param address String wallet address to withdraw money into
     * @param amount String amount of asset to be withdrawn
     * @return
     */
    @PostMapping("/binance/withdraw")
    public WithdrawResult makeBinanceWithdrawl(@QueryParam(value = "asset") String asset,
                                               @QueryParam(value = "address") String address,
                                               @QueryParam(value = "amount") String amount) {
        return binanceAccount.makeWithdrawl(asset, address, amount);
    }

    /**
     * Binance: [POST] Test market trade
     *
     * @param pair String asset pair, case & order sensitive (e.g. ETHBTC, BTCETH, LTCBTC etc.)
     * @param amount String amount of asset to be traded
     */
    @PostMapping("/binance/tradetest")
    public void makeBinanceTradeTest(@QueryParam(value = "pair") String pair,
                                               @QueryParam(value = "amount") String amount) {
        binanceTrade.testMarketOrder(pair, amount);
    }

}
