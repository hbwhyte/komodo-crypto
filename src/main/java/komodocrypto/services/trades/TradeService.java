package komodocrypto.services.trades;

import komodocrypto.exceptions.custom_exceptions.InsufficientFundsException;
import komodocrypto.mappers.database.CurrencyMapper;
import komodocrypto.mappers.database.CurrencyPairsMapper;
import komodocrypto.mappers.database.TransactionMapper;
import komodocrypto.mappers.exchanges.ExchangeInfoMapper;
import komodocrypto.mappers.exchanges.ExchangeWalletMapper;
import komodocrypto.model.TradeData;
import komodocrypto.model.database.Transaction;
import komodocrypto.model.exchanges.ExchangeWallet;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TradeService {

    /*  This class should serve as an intermediary between the arbitrage scanning service and the asset tracking
        service. When an arbitrage opportunity is identified and persisted, the methods in this class should
        1)  Call exchange wallet mapper to see if selling currency is in selling wallet
            a) If not, break from execution.
        2)  If so, call asset tracking service to...
            a) Create record of transaction
            b) Remove all of the currency to sell out of the selling wallet
            c) Add it to the buying wallet

     */

    Logger logger = LoggerFactory.getLogger(this.getClass());
    TradeData tradeData = new TradeData();

    @Autowired
    CurrencyMapper currencyMapper;

    @Autowired
    CurrencyPairsMapper currencyPairsMapper;

    @Autowired
    ExchangeInfoMapper exchangeInfoMapper;

    @Autowired
    ExchangeWalletMapper exchangeWalletMapper;

    @Autowired
    TransactionMapper transactionMapper;

    // Builds a TradeData object, containing the information needed to execute a trade and persist its data.
    // NOTE: May need to overload this method or something when doing manual trades.
    // TODO Look into how to loosely couple the TradeData object with the classes where it's used.
    public void buildTradeData(Exchange[] exchanges, CurrencyPair currencyPair, Timestamp timeArbOppIdentified) throws InsufficientFundsException {

        logger.info("Building trade model...");

        tradeData.setFromExchange(exchanges[0]);
        tradeData.setToExchange(exchanges[1]);
        tradeData.setCurrencyPair(currencyPair);

        int idFromExchange = exchangeInfoMapper.getExchangeIdByName(exchanges[0].getExchangeSpecification().getExchangeName());
        int idToExchange = exchangeInfoMapper.getExchangeIdByName(exchanges[1].getExchangeSpecification().getExchangeName());
        tradeData.setIdFromExchange(idFromExchange);
        tradeData.setIdToExchange(idToExchange);

        String cpString = currencyPair.toString();
        int idFromCurrency = currencyMapper.getIdBySymbol(cpString.substring(0, cpString.indexOf("/")));
        int idToCurrency = currencyMapper.getIdBySymbol(cpString.substring(cpString.indexOf("/") + 1));
        tradeData.setIdFromCurrency(idFromCurrency);
        tradeData.setIdToCurrency(idToCurrency);
        tradeData.setIdCurrencyPair(currencyPairsMapper.getCurrencyPairId(idFromCurrency, idToCurrency));

        // Throws an exception for now if there is no record of this currency pair in the exchange wallet or if the
        // available balance is 0.
        // TODO Find a better way to handle this.
        try {

            BigDecimal balanceFromExchange = exchangeWalletMapper.getBalanceByExchangeIdAndCurrencyId(idFromExchange, idFromCurrency);
            tradeData.setBalanceFromExchange(balanceFromExchange);

            if (balanceFromExchange.compareTo(BigDecimal.valueOf(0)) <= 0) throw new Exception();

        } catch (Exception e) {

            throw new InsufficientFundsException("Insufficient funds to trade in exchange " +
                    exchanges[0].getExchangeSpecification().getExchangeName() + ".", HttpStatus.OK);
        }

        try {

            BigDecimal balanceToExchange = exchangeWalletMapper.getBalanceByExchangeIdAndCurrencyId(idToExchange, idToCurrency);
            tradeData.setBalanceToExchange(balanceToExchange);

            if (balanceToExchange.compareTo(BigDecimal.valueOf(0)) <= 0) throw new Exception();

        } catch (Exception e) {

            throw new InsufficientFundsException("Insufficient funds to trade in exchange " +
                    exchanges[1].getExchangeSpecification().getExchangeName() + ".", HttpStatus.OK);
        }

        tradeData.setTimeArbOppIdentified(timeArbOppIdentified);

        logger.info("Finished building trade model.");
    }

    // Executes the trade.
    public void executeTrade() {

        logger.info("Executing trade...");

        /*  Will eventually make actual trades with exchanges and confirm that the data used to updated the database is
            accurate, but for now we'll just update the tables ourselves. */

        // Creates new transaction logs reflecting the trade.
        Transaction transactionFrom = new Transaction();
        transactionFrom.setExchange_id(tradeData.getIdFromExchange());
        transactionFrom.setCurrency_pair_id(tradeData.getIdCurrencyPair());
        transactionFrom.setTransaction_type("sell");
        BigDecimal balanceFrom = tradeData.getBalanceFromExchange();
        transactionFrom.setTransaction_amount(balanceFrom);
        transactionFrom.setBalance_before_transaction(balanceFrom);
        transactionFrom.setAlgorithm("arbitrage");

        Transaction transactionTo = new Transaction();
        transactionTo.setExchange_id(tradeData.getIdToExchange());
        transactionTo.setCurrency_pair_id(tradeData.getIdCurrencyPair());
        transactionTo.setTransaction_type("buy");
        BigDecimal balanceTo = tradeData.getBalanceToExchange();
        transactionTo.setTransaction_amount(balanceFrom);
        transactionTo.setBalance_before_transaction(balanceTo);
        transactionTo.setAlgorithm("arbitrage");

        // Adds the new entries to the transaction table.
        transactionMapper.addTransaction(transactionFrom);
        transactionMapper.addTransaction(transactionTo);
        
        // Creates and initializes the values in the POJO used to update the exchange wallet table.
        ExchangeWallet fromWallet = new ExchangeWallet();
        fromWallet.setExchangeId(tradeData.getIdFromExchange());
        fromWallet.setCurrencyId(tradeData.getIdFromCurrency());
        fromWallet.setAvailable(BigDecimal.valueOf(0));

        ExchangeWallet toWallet = new ExchangeWallet();
        toWallet.setExchangeId(tradeData.getIdToExchange());
        toWallet.setCurrencyId(tradeData.getIdToCurrency());
        toWallet.setAvailable(BigDecimal.valueOf(0));

        // Adds new entries to the exchange wallet table reflecting the trade.
        exchangeWalletMapper.insertNewData(fromWallet);
        exchangeWalletMapper.insertNewData(toWallet);

        // TODO Deal with client & group portfolios -- By what criteria do we update the balance?

        logger.info("Finished executing the trade.");
    }
}
