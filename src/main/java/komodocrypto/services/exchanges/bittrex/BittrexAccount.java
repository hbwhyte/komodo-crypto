package komodocrypto.services.exchanges.bittrex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import komodocrypto.configuration.exchange_utils.BittrexUtil;
import komodocrypto.services.exchanges.interfaces.ExchangeAccountService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
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
public class BittrexAccount implements ExchangeAccountService{

    private AccountService accountService;
    private AccountInfo accountInfo;

    public BittrexAccount() {
        this.accountService = setupAccountService();
        try {
            this.accountInfo = accountService.getAccountInfo();
        } catch (IOException e) {
            System.out.println("IOException at BittrexAccount()");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        BittrexAccount bittrexAccount = new BittrexAccount();
        System.out.println("Username: " + bittrexAccount.getUsername() );
        System.out.println("Deposit Address for BTC: " + bittrexAccount.getDepositAddress(Currency.BTC));
        System.out.println("Trading fee: " + bittrexAccount.getTradingFee());
        //bittrexAccount.generic(bittrexAccount.setupAccountService());
        System.out.println("USDT Balance : " + bittrexAccount.getCurrencyBalance(Currency.USDT));
    }

    public AccountService setupAccountService(){
        BittrexUtil bittrexUtil = new BittrexUtil();
        Exchange bittrex = bittrexUtil.createExchange();
        AccountService accountService = bittrex.getAccountService();

        return accountService;
    }
    public AccountInfo accountInfo() throws IOException {
        AccountService accountService = setupAccountService();
        return accountService.getAccountInfo();
    }

    public String getUsername() throws IOException {
        String username = this.accountInfo.getUsername();
        return username;
    }

    public BigDecimal getTradingFee() throws IOException {
        BigDecimal tradingFee = this.accountInfo.getTradingFee();
        return tradingFee;
    }

    @Override
    public String getDepositAddress(Currency currency) {
        String depositAddress = null;
        try {
            depositAddress = this.accountService.requestDepositAddress(currency);
        } catch (IOException e) {
            System.out.println("IOException while trying to get the deposit address");
            e.printStackTrace();
        }
        return depositAddress;
    }

    @Override
    public Balance getCurrencyBalance(Currency currency) {
        Balance balance = accountInfo.getWallet().getBalance(currency);
        return balance;
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal quantity, String address) {
        String transactionID = null;
        try {
            transactionID = this.accountService.withdrawFunds(currency, quantity, address);
        } catch (IOException e) {
            System.out.println("IOException while withdrawing funds");
            e.printStackTrace();
        }
        return transactionID;
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


    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }
}
