package komodocrypto.services.exchanges.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import komodocrypto.configuration.exchange_utils.BitstampUtil;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Bitstamp exchange with authentication
 *   <li>View account balance
 *   <li>Get the bitcoin deposit address
 *   <li>List unconfirmed deposits (raw interface only)
 *   <li>List recent withdrawals (raw interface only)
 *   <li>Withdraw a small amount of BTC
 * </ul>
 */
public class BitstampAccount {

    public static void main(String[] args) throws IOException {

        Exchange bitstamp = BitstampUtil.createExchange();
        AccountService accountService = bitstamp.getAccountService();

        generic(accountService);
    }

    private static void generic(AccountService accountService) throws IOException {

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
