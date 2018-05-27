package komodocrypto.services.asset_management;

public class AssetService {

    public void updatePortfolioAfterDepositOrWithdrawal(double amount) {

        /*  how is the user linked to this?

            this method has been called to make a deposit or withdrawal of the specified amount (USD)

            // set to 1 if a new user is making a deposit, otherwise 0
            numNewInvestors = 0

            // set to 1 or -1 depending on whether the transaction is a deposit or withdrawal
            sign = 1

            is this a new user?
                select all users where id or names = id or names of user making transaction -- see how user system works
                NOTE: if it's a new user they are making a deposit

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
                        removeAssetsAcrossExchanges()

            totalValue = get current_value from group portfolio
            shareOwnership = amount / totalValue

            add user id, deposit value, (get current value from prev entry + amount), %ownership to client portfolio
        */
    }

    public void distributeAssetsAcrossExchanges(double amount, int sign) {

        /*  make sure assets are evenly distributed across supported exchanges
                need list of exchanges
                split = amount / number of exchanges
                for each exchange
                    insert into exchange wallet `total`, (get total from previous entry + split * sign)
        */
    }

    public void removeAssetsAcrossExchanges() {

        /*

         */
    }
}
