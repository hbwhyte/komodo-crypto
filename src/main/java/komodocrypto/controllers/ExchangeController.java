package komodocrypto.controllers;

import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.WithdrawResult;
import komodocrypto.model.RootResponse;
import komodocrypto.services.exchanges.binance.BinanceAccount;
import komodocrypto.services.exchanges.bitstamp.BitstampAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
public class ExchangeController {

    @Autowired
    BitstampAccount bitstampAccount;

    @Autowired
    BinanceAccount binanceAccount;

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
     * Binance: [POST] Withdraw funds
     *
     * @param asset asset symbol, case sensitive (e.g. ETH, BTC, LTC etc.)
     * @param address wallet address to withdraw money into
     * @param amount amount of asset to be withdrawn
     * @return
     */
    @PostMapping("/binance/withdraw")
    public WithdrawResult makeBinanceWithdrawl(@PathVariable(value = "asset") String asset,
                                               @PathVariable(value = "address") String address,
                                               @PathVariable(value = "amount") String amount) {
        return binanceAccount.makeWithdrawl(asset, address, amount);
    }


}
