package komodocrypto.services.exchanges.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import komodocrypto.configuration.exchange_utils.BitstampUtil;
import komodocrypto.exceptions.custom_exceptions.ExchangeConnectionException;
import komodocrypto.model.exchanges.BitstampBalance;
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

import javax.annotation.PostConstruct;


@Service
public class BitstampAccount {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    BitstampUtil bitstampUtil;

    /**
     * Returns account balance for a given asset from Bitstamp
     *
     * @return BitstampBalance object
     * @throws ExchangeConnectionException if unable to connect to exchange
     */
    public BitstampBalance getBalance(Currency asset) throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        AccountService accountService = bitstamp.getAccountService();
        // Get account info object
        AccountInfo accountInfo;
        logger.info("Requesting Bitstamp account history...");
        try {
            accountInfo = accountService.getAccountInfo();
            logger.info("Account info retrieved.");
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to get account information", HttpStatus.BAD_GATEWAY);
        }

        // Map raw to BitstampBalance object
        BitstampBalance balance = new BitstampBalance();
        balance.setCurrency(asset.toString());
        balance.setTotal(accountInfo.getWallet().getBalance(asset).getTotal());
        balance.setAvailable(accountInfo.getWallet().getBalance(asset).getAvailable());
        balance.setFrozen(accountInfo.getWallet().getBalance(asset).getFrozen());
        balance.setBorrowed(accountInfo.getWallet().getBalance(asset).getBorrowed());
        balance.setLoaned(accountInfo.getWallet().getBalance(asset).getLoaned());
        balance.setWithdrawing(accountInfo.getWallet().getBalance(asset).getWithdrawing());
        balance.setDepositing(accountInfo.getWallet().getBalance(asset).getDepositing());

        return balance;
    }

    /**
     * Returns trade history from Bitstamp
     *
     * @return List of Funding Records
     * @throws ExchangeConnectionException if unable to connect to exchange
     */
    public List<FundingRecord> getTradeHistory() throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        AccountService accountService = bitstamp.getAccountService();

        // Get transaction history
        // Only works if you have transaction history.
        logger.info("Requesting Bitstamp trade history...");
        TradeHistoryParams tradeHistoryParams = accountService.createFundingHistoryParams();
        try {
            List<FundingRecord> fundingRecords = accountService.getFundingHistory(tradeHistoryParams);
            logger.info("Trade history retrieved.");
            return fundingRecords;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to get trade history", HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * Submit a withdraw request.
     * <p>
     * Enable Withdrawals option has to be active in the API settings.
     *
     * @param asset   Currency asset symbol to withdraw
     * @param amount  BigDecimal amount of asset to be withdrawn
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
            throw new ExchangeConnectionException("Unable to withdraw funds", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Get the deposit address for a given asset, on Bitstamp for the
     * connected account.
     *
     * @param asset Currency asset symbol
     * @return String of the deposit address for a given asset
     * @throws ExchangeConnectionException if unable to connect to exchange
     */
    public String getDepositAddress(Currency asset) throws ExchangeConnectionException {
        // Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        // Connect to account
        AccountService accountService = bitstamp.getAccountService();

        // Return deposit address for given asset
        logger.info("Requesting Bitstamp deposit address for " + asset + "...");
        try {
            String depositAddress = accountService.requestDepositAddress(asset);
            logger.info("Deposit address retrieved.");
            return depositAddress;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to generate deposit address", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Returns the trading fee for the Bitstamp exchange.
     * @return a BigDecimila with the trading fee percentage.
     * @throws IOException
     * @throws ExchangeConnectionException
     */
    public BigDecimal getTradingFee() throws IOException, ExchangeConnectionException {
        //Create Exchange
        Exchange bitstamp = bitstampUtil.createExchange();
        //Connect to account
        AccountService accountService = bitstamp.getAccountService();

        // Return deposit address for given asset
        logger.info("Requesting Bitstamp trading fee...");
        try {
            BigDecimal tradingFee = accountService.getAccountInfo().getTradingFee();
            logger.info("Trading fee retrieved = " + tradingFee);
            return tradingFee;
        } catch (IOException e) {
            throw new ExchangeConnectionException("Unable to get trading fee", HttpStatus.BAD_REQUEST);
        }
    }

}