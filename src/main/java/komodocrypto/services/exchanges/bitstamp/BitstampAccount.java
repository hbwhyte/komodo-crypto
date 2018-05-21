package komodocrypto.services.exchanges.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.List;

import komodocrypto.configuration.exchange_utils.BitstampUtil;
import komodocrypto.exceptions.custom_exceptions.ExchangeConnectionException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
@Service
public class BitstampAccount {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    BitstampUtil bitstampUtil;

    public AccountService accountInfo() throws IOException {
        BitstampUtil obj = new BitstampUtil();
        Exchange bitstamp = obj.createExchange();
        AccountService accountService = bitstamp.getAccountService();

        generic(accountService);
        return accountService;
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

    }

    /**
     * Submit a withdraw request.
     *
     * Enable Withdrawals option has to be active in the API settings.
     *
     * @param asset Currency asset symbol to withdraw
     * @param amount BigDecimal amount of asset to be withdrawn
     * @param address String deposit address to send withdraw funds into
     * @return String
     * @throws ExchangeConnectionException if unable to connect to exchange
     */
    public String makeWithdrawl(Currency asset, BigDecimal amount, String address) throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        AccountService accountService = bitstamp.getAccountService();
        // Withdraw funds
        logger.info("Withdrawing " + amount + " " + asset + " to " + address + "...");
        try {
            String withdrawResult = accountService.withdrawFunds(asset, amount, address);
            logger.info("Withdrawl complete.");
            return withdrawResult;
        } catch (IOException e) {
           throw new ExchangeConnectionException("Unable to withdraw funds at this time", HttpStatus.BAD_REQUEST);
        }
    }




}