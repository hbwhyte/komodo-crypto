package komodocrypto.services.asset_tracking;

import komodocrypto.exceptions.custom_exceptions.InsufficientFundsException;
import komodocrypto.mappers.database.ArbitrageTradeHistoryMapper;
import komodocrypto.mappers.database.ClientPortfolioMapper;
import komodocrypto.mappers.database.GroupPortfolioMapper;
import komodocrypto.mappers.database.TransactionMapper;
import komodocrypto.mappers.exchanges.ExchangeWalletMapper;
import komodocrypto.mappers.user.UserMapper;
import komodocrypto.model.TradeData;
import komodocrypto.model.database.Currency;
import komodocrypto.model.database.GroupPortfolio;
import komodocrypto.model.database.Transaction;
import komodocrypto.model.exchanges.ExchangeWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AssetTrackingService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    GroupPortfolioMapper groupPortfolioMapper;

    @Autowired
    ClientPortfolioMapper clientPortfolioMapper;

    @Autowired
    ExchangeWalletMapper exchangeWalletMapper;

    @Autowired
    ExchangeWallet exchangeWallet;

    @Autowired
    ArbitrageTradeHistoryMapper arbitrageTradeHistoryMapper;

    /*  Contains the following methods:
        Transaction recalculateBalance() - creates a new Transaction object with transaction data -- DOES NOT YET PERSIST
        Transaction updateBalance() - Calls exchange APIs for transfer, should
        void updateAssetsUnderManagement() -
        void updatePortfolioAfterDepositOrWithdrawal() -
        void distributeAssetsAcrossExchanges() -
        void removeAssetsAcrossExchanges() -
        void rebalanceAssetsAcrossExchanges() -
     */

    //updateAssetsUnderManagement() - this method will be called when the Ivan's trade executes
    // - he can pass this method any/all information he has about that trade:
    //                    - the currency
    //                    - the exchanges
    //                    - the prices
    //                    - the buy/sell amounts


    public Transaction recalculateBalance(Exchange exchange, Currency currency, BigDecimal amount, String transactionType, String algorithm) {

        Transaction transaction = new Transaction();

        int currencyId = currency.getCurrency_id();
        int exchangeId = exchange.getExchange_id();
        BigDecimal fee = BigDecimal.valueOf(0);

        switch (transactionType) {
            case "buy":
                fee = exchange.getBuy_fee();
                break;
            case "sell":
                fee = exchange.getSell_fee();
                break;
            case "transfer":
                fee = exchange.getTransfer_fee();
                break;
        }

        BigDecimal feeAmount = amount.multiply(fee);
        BigDecimal balanceBefore = transactionMapper.getBalanceBeforeTransaction(exchangeId, currencyId);
        if (balanceBefore == null) {

            transaction.setBalance_before_transaction(BigDecimal.valueOf(0));

        } else {
            transaction.setBalance_before_transaction(balanceBefore);

        }

        BigDecimal balanceAfterTransaction = balanceBefore.subtract(feeAmount);

        transaction.setCurrency_id(currencyId);
        transaction.setExchange_id(exchangeId);
        transaction.setTransaction_type(transactionType);
        transaction.setTransaction_amount(amount.subtract(fee));
        transaction.setTransaction_fee(feeAmount);
        transaction.setAlgorithm(algorithm);
        transaction.setBalance_after_transaction(balanceAfterTransaction);

        /* recalculate balance after buy/sell transaction
                check balance on the exchange wallet of the selected currency
                total1 = select total where exchange_id = exchange_name.getExchange_id and currency_id = currency_pair_id.getSymbol1
                total2 = select total where exchange_id = exchange_name.getExchange_id and currency_id = currency_pair_id.getSymbol2
                newTotal1 = total1 - sellAmount
                newTotal2 = total2 + buyAmount
            */
    }


    public Transaction updateBalance(TradeData tradeData) {

        Transaction tldata = new Transaction();

       /* insert new balance after buy/sell transaction
                api call to related exchanges

         */

        /* recalculate balance after transfer
                will have to take in the 2 exchanges where the money is transferred from and to
         */
        return tldata;
    }

    public void updateAssetsUnderManagement(int buy_exchange_id, int sell_exchange_id, Exchange exchange_name,
                                            double price, double amount, String status, int currency_pair_id){

        /* transactionType determines buy, sell, or transfer
                if buy
                    buyAmount = amount*price-exchangename.getBuy_fee
                if sell
                    sellAmount = amount*price-exchangename.getSell_fee
                if transfer
                    transferAmount = amount*price-exchangename.getTransfer_fee
         */

         /* recalculate balance after buy/sell transaction
                check balance on the exchange wallet of the selected currency
                total1 = select total where exchange_id = exchange_name.getExchange_id and currency_id = currency_pair_id.getSymbol1
                total2 = select total where exchange_id = exchange_name.getExchange_id and currency_id = currency_pair_id.getSymbol2
                newTotal1 = total1 - sellAmount
                newTotal2 = total2 + buyAmount

         */

         /* recalculate balance after transfer
                will have to take in the 2 exchanges where the money is transferred from and to
         */

        /* parse new data to exchange wallet
                not sure what frozen, borrowed, loaned, etc means
         */

        /* update the balance of both currencies where the exchange happens on the arbitrage_trade_history table
                insert currency_pair_id
                insert token_price
                    arbitrageTradeHistory.setTokenPrice = price
                insert token_amount
                    arbitrageTradeHistory.setTokenAmount = buy/sell/transferAmount
                insert order_status
                    arbitrageTradeHistory.setOrderStatus = status
                insert buy_exchange_id
                insert sell_exchange_id
         */

        /* update the balance in group_portfolio after transaction?
                should there be a column for buy & sell amount here (considering there is a deposit column)?
                current_value = getTokenAmount * usdExchangeRate
         */
    }

    // For now, I'm assuming that it's a deposit or withdrawal by the sign on "amount".
    public void updatePortfolioAfterDepositOrWithdrawal(int userId, double amount) throws InsufficientFundsException {

        /*  user userId has called this method to make a deposit or withdrawal of the specified amount (USD)

            // set to 1 if a new user is making a deposit, otherwise 0
            numNewInvestors = 0

            // set to 1 or -1 depending on whether the transaction is a deposit or withdrawal
            sign = 1

            is this a new user?
                select deposit_value where id or names = id or names of user making transaction
                NOTE: if it's a new user they are making a deposit
                count of deposits for user id will return 0 if no deposits have been made

            is this transaction a deposit or withdrawal?
                if deposit
                    if new user
                        numNewInvestors = 1
                    add deposit value, (deposit value * sign + get current value from previous entry),
                        (get num_investors from previous entry + numNewInvestors) to group portfolio
                    distributeAssetsAcrossExchanges(amount, sign)
                if withdrawal
                    sign = -1
                    get current_value from last entry in client portfolio for this client

                    if amount > current_value -- are they trying to withdraw more than they have?
                        throw InsufficientFundsException
                    add deposit value, (deposit value * sign + get current value from previous entry),
                    (get num_investors from previous entry) to group portfolio

                    if amount == current value -- is this a withdrawal of all funds?
                        distributeAssetsAcrossExchanges(amount, sign)
                    else if amount < current value
                        removeAssetsAcrossExchanges(amount)

            totalValue = get current_value from group portfolio
            shareOwnership = amount / totalValue

            add user id, deposit value, (get current value from prev entry + amount), %ownership to client portfolio
        */

        // Set to 1 if a new user is making a deposit, otherwise 0
        int numNewInvestors = 0;

        // Checks if this user has made a deposit. If not, sets numNewInvestors to 1.
        if (clientPortfolioMapper.getNumDepositsById(userId) == 0) numNewInvestors = 1;

        // The latest total value of the group portfolio, the BigDecimal value of the new deposit, and the number of
        // investors
        BigDecimal currentValueGroup = groupPortfolioMapper.getCurrentValue();
        BigDecimal depositOrWithdrawalValue = BigDecimal.valueOf(amount);
        int numInvestors = groupPortfolioMapper.getNumInvestors();

        // Creates and initializes the values of a new GroupPortfolio object for inserting into its eponymous table.
        GroupPortfolio groupPortfolio = new GroupPortfolio();
        groupPortfolio.setDeposit_value(depositOrWithdrawalValue);
        groupPortfolio.setCurrent_value(currentValueGroup.add(depositOrWithdrawalValue));
        groupPortfolio.setNum_investors(numInvestors + numNewInvestors);

        // Checks if this transaction is a deposit or withdrawal and adds or subtracts it and distributes and rebalances
        // funds across the exchanges as necessary.
        if (amount > 0) {

            groupPortfolioMapper.addEntry(groupPortfolio);
            distributeAssetsAcrossExchanges(amount);

        } else if (amount < 0) {

            // Gets the current value of this user's portfolio.
            BigDecimal currentValueClient = clientPortfolioMapper.getCurrentValue(userId);

            // Throws an exception if the user tries to withdraw more than he has.
            if (depositOrWithdrawalValue.compareTo(currentValueClient) == 1)
                throw new InsufficientFundsException("Requested withdrawal amount exceeds portfolio's value.",
                        HttpStatus.BAD_REQUEST);

            // Subtracts the withdrawal amount from the group portfolio.
            // By definition, the user portfolio's value will not exceed that of the group portfolio.
            groupPortfolioMapper.addEntry(groupPortfolio);

            // Subtracts the withdrawal value from each exchange evenly as long as the it is less than or equal to the
            // client portfolio's value.
            if (depositOrWithdrawalValue.compareTo(currentValueClient) <= 0)
                removeAssetsAcrossExchanges(amount);
        }
    }

    public void distributeAssetsAcrossExchanges(double amount) {

        /*  make sure assets are evenly distributed across supported exchanges
                need list of exchanges
                split = amount / number of exchanges
                for each exchange
                    insert into exchange wallet `total`, (get total from previous entry + split * sign)
        */

    }

    public void removeAssetsAcrossExchanges(double amount) {

        /*  ArrayList<ExchangeWallet> exchangeWallet = exchangeWalletMapper.getExchangeWallets();
            for each exchangeWallet
                is amount > funds?
                    NOTE: if there is only one investor, the total in their wallet should never exceed total funds across exchanges
                    withdraw all funds from exchange -- update db
                    WHERE ARE THE FUNDS TRANSFERRED TO THE IN DATABASE?
                    amount = amount - funds
                    rebalance()
                is amount <= funds?
                    withdraw(funds) -- update db
                    rebalance()
                    break
         */
        // The amount in BigDecimal form
        BigDecimal withdrawalValue = BigDecimal.valueOf(amount);

        // A list of the exchanges
        List<ExchangeWallet> wallets = exchangeWalletMapper.getExchangeWallets();

        for (int i = 0; i < wallets.size(); i++) {

            ExchangeWallet currentWallet = wallets.get(i);
            BigDecimal fundsInWallet = currentWallet.getAvailable();

            if (withdrawalValue.compareTo(fundsInWallet) == 1) {

                currentWallet.setAvailable(BigDecimal.valueOf(0));
                withdrawalValue = withdrawalValue.subtract(fundsInWallet);
                rebalanceAssetsAcrossExchanges(wallets);

            } else {

                BigDecimal fundsAfterWithdrawal = fundsInWallet.subtract(withdrawalValue);
                currentWallet.setAvailable(fundsAfterWithdrawal);
                rebalanceAssetsAcrossExchanges(wallets);
                return;
            }
        }
    }

    public void rebalanceAssetsAcrossExchanges(List<ExchangeWallet> wallets) {

        /*  ArrayList<ExchangeWallet> exchangeWallet = exchangeWalletMapper.getExchangeWallets();
            BigDecimal sum = 0
            BigDecimal average = 0
            for each exchangeWallet e
                sum.add(e)
            average = sum.divide(exchangeWallet.size())
            for each exchangeWallet e
                difference = average - funds -- does the exchange have more funds than average?
                is difference > 0? -- does it not have as much as the average?
                    add abs(difference)
                else -- if it has more than average
                    subtract abs(difference)

         */

        BigDecimal sum = BigDecimal.valueOf(0);
        BigDecimal average;
        BigDecimal fundsInWallet;

        for (ExchangeWallet w : wallets) {

            fundsInWallet = w.getAvailable();
            sum = sum.add(fundsInWallet);
        }

        average = sum.divide(BigDecimal.valueOf(wallets.size()));

        for (ExchangeWallet w : wallets) {

            fundsInWallet = w.getAvailable();
            BigDecimal difference = average.subtract(fundsInWallet);

            if (difference.compareTo(BigDecimal.valueOf(0)) == 1)
                w.setAvailable(fundsInWallet.add(difference.abs()));

            else w.setAvailable(fundsInWallet.subtract(difference.abs()));

            exchangeWalletMapper.insertNewData(w);
        }
    }
}
