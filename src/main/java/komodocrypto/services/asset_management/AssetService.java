package komodocrypto.services.asset_management;

import komodocrypto.exceptions.custom_exceptions.InsufficientFundsException;
import komodocrypto.mappers.database.ClientPortfolioMapper;
import komodocrypto.mappers.database.GroupPortfolioMapper;
import komodocrypto.mappers.exchanges.ExchangeWalletMapper;
import komodocrypto.mappers.user.UserMapper;
import komodocrypto.model.database.GroupPortfolio;
import komodocrypto.model.exchanges.ExchangeWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

public class AssetService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    GroupPortfolioMapper groupPortfolioMapper;

    @Autowired
    ClientPortfolioMapper clientPortfolioMapper;

    @Autowired
    ExchangeWalletMapper exchangeWalletMapper;

    @Autowired
    ExchangeWallet exchangeWallet;

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
            if (depositOrWithdrawalValue.compareTo(currentValueClient) == 0 ||
                    depositOrWithdrawalValue.compareTo(currentValueClient) == -1)
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
        }
    }
}
