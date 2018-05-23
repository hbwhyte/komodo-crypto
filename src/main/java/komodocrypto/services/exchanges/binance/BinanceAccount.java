package komodocrypto.services.exchanges.binance;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.DepositAddress;
import com.binance.api.client.domain.account.Trade;

import com.binance.api.client.domain.account.WithdrawResult;
import komodocrypto.configuration.exchange_utils.BinanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BinanceAccount {

    @Autowired
    BinanceUtil binanceUtil;

    /**
     * Prints out potentailly relevant account info
     */
    public Account getAccountInfo() {
        BinanceApiRestClient client = binanceUtil.createExchange();

        // Get account balances
        Account account = client.getAccount(6000000L, System.currentTimeMillis());
        System.out.println(account.getBalances());
        System.out.println(account.getAssetBalance("ETH"));

        // Get list of trades
        List<Trade> myTrades = client.getMyTrades("NEOETH");
        System.out.println(myTrades);

        // Get withdraw history
        System.out.println(client.getWithdrawHistory("ETH"));

        // Get deposit history
        System.out.println(client.getDepositHistory("ETH"));

        // Get deposit address
        System.out.println(client.getDepositAddress("ETH"));

        // Get update time
        System.out.println(client.getAccount().getUpdateTime());

        // Get maker commission
        System.out.println(client.getAccount().getMakerCommission());

        // Get taker commission
        System.out.println(client.getAccount().getTakerCommission());

        return account;

    }
    /**
     * Submit a withdraw request.
     *
     * Enable Withdrawals option has to be active in the API settings.
     *
     * @param asset Sting asset symbol to withdraw
     * @param address address to withdraw to
     * @param amount amount to withdraw
     */
    public WithdrawResult makeWithdrawl(String asset, String address, String amount) {
        // create exchange instance
        BinanceApiRestClient client = binanceUtil.createExchange();

        // Make withdrawl, return result
        return client.withdraw(asset, address, amount, null, null); // s3 is address alias, s4 is secondary address identifier
    }

    /**
     * Get the deposit address for a given asset, on Binance for the
     * connected account.
     *
     * @param asset String asset symbol
     * @return DepositAddress
     */
    public DepositAddress getDepositAddress(String asset) {
        // create exchange instance
        BinanceApiRestClient client = binanceUtil.createExchange();

        // Return deposit address for given asset
        return client.getDepositAddress(asset);
    }

    /**
     * Async version of account info/withdrawls
     *
     * @throws InterruptedException
     */
    public void getAsyncAccountInfo() throws InterruptedException {

        BinanceApiAsyncRestClient client = binanceUtil.createAsyncExchange();

        // Test connectivity
        client.ping(response -> System.out.println("Ping succeeded."));

        // Get account balances (async)
        client.getAccount((Account response) -> System.out.println(response.getBalances()));

        // Get list of trades (async)
        client.getMyTrades("ETHBTC", System.out::println);

        // Get withdraw history (async)
        client.getWithdrawHistory("BTC", System.out::println);

        // Get deposit history (async)
        client.getDepositHistory("BTC", System.out::println);

        // Withdraw (async)
        client.withdraw("ETH", "0x123", "0.1", null, null, response -> {
        });

        client.getAccount((Account response) -> System.out.println("GUT: " + response.getUpdateTime()));
        client.getAccount((Account response) -> System.out.println("GMC: " + response.getMakerCommission()));
        client.getAccount((Account response) -> System.out.println("GTC: " + response.getTakerCommission()));
    }

}

