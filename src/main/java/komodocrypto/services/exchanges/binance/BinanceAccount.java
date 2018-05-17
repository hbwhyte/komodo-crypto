package komodocrypto.services.exchanges.binance;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.Trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinanceAccount {

    public static String apiKey = "wUvk98wSzeu1Xbr5FTx438h1vjvR5rBsRkEUCfAVi55K65Tal5wINEjnWULpYenf";

    public static String secretKey = "RzvvWGZGWEMMgAUxs6ZykV548V3gnL1nKikvTQs2WYpkznuFuP1Mx59hCFciH6am";


    public Account getAccountInfo() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, secretKey);
        BinanceApiRestClient client = factory.newRestClient();

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

        // Withdraw
        client.withdraw("ETH", "0x123", "0.1", null, null);

        // Get update time
        System.out.println(client.getAccount().getUpdateTime());

        // Get maker commission
        System.out.println(client.getAccount().getMakerCommission());

        // Get taker commission
        System.out.println(client.getAccount().getTakerCommission());

        return account;

    }

    public void getAsyncAccountInfo() throws InterruptedException {

        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, secretKey);
        BinanceApiAsyncRestClient client = factory.newAsyncRestClient();

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

