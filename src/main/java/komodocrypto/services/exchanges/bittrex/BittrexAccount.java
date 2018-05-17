package komodocrypto.services.exchanges.bittrex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import komodocrypto.configuration.exchange_utils.BittrexUtil;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Bitstamp exchange with authentication</li>
 *   <li>View account balance</li>
 *   <li>Get the bitcoin deposit address</li>
 *   <li>List unconfirmed deposits (raw interface only)</li>
 *   <li>List recent withdrawals (raw interface only)</li>
 *   <li>Withdraw a small amount of BTC</li>
 * </ul>
 */
@Service
public class BittrexAccount {


    @Autowired
    BittrexUtil bittrexUtil;

    public static void main(String[] args) throws IOException {
        BittrexAccount bittrexAccount = new BittrexAccount();
        bittrexAccount.accountInfo();

    }
    public void accountInfo() throws IOException {

        Exchange bittrex = bittrexUtil.createExchange();
        AccountService accountService = bittrex.getAccountService();

        getUsername(accountService);
        getDepositAddress(accountService, Currency.BTC);
        //generic(accountService);
    }

    public String getUsername(AccountService accountService) throws IOException {
        String username = accountService.getAccountInfo().getUsername();
        System.out.println(username);
        return username;
    }

    public String getAccountInfo(AccountService accountService) throws IOException {
        String accountInfo = accountService.getAccountInfo().toString();
        System.out.println(accountInfo);
        return accountInfo;

    }
    public String getDepositAddress(AccountService accountService, Currency currency) throws IOException {
        String depositAddress = accountService.requestDepositAddress(currency);
        System.out.println("Deposit address: " + depositAddress);
        return depositAddress;
    }
    private void generic(AccountService accountService) throws IOException {

        // Get the account information
        AccountInfo accountInfo = accountService.getAccountInfo();
        System.out.println("AccountInfo as String: " + accountInfo.toString());

        String depositAddress = accountService.requestDepositAddress(Currency.BTC);
        System.out.println("Deposit address: " + depositAddress);

        TradeHistoryParams tradeHistoryParams = accountService.createFundingHistoryParams();
        List<FundingRecord> fundingRecords = accountService.getFundingHistory(tradeHistoryParams);
        // Only works if you have transaction history. I do not.
        for (FundingRecord record : fundingRecords) {
            System.out.println(record.getStatus());
            System.out.println(record.getBlockchainTransactionHash());
            System.out.println(record.getAddress());
            System.out.println(record.getInternalId());
        }

        // WARNING: WILL WITHDRAW COINS IF THERE IS AN ADDRESS
        // Address is set to "XXX" so will throw an error, but don't actually use this to withdraw money.
        String withdrawResult =
                accountService.withdrawFunds(Currency.BTC, new BigDecimal(1).movePointLeft(4), "XXX");
        System.out.println("withdrawResult = " + withdrawResult);
    }
}
