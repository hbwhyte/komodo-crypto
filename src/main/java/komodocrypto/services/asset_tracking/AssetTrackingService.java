package komodocrypto.services.asset_tracking;

import komodocrypto.mappers.database.ArbitrageTradeHistoryMapper;
import komodocrypto.mappers.database.GroupPortfolioMapper;
import komodocrypto.mappers.database.TransactionMapper;
import komodocrypto.model.database.Currency;
import komodocrypto.model.database.Exchange;
import komodocrypto.model.database.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class AssetTrackingService {

    @Autowired
    GroupPortfolioMapper groupPortfolioMapper;

    @Autowired
    ArbitrageTradeHistoryMapper arbitrageTradeHistoryMapper;

    @Autowired
    TransactionMapper transactionMapper;


    //updateAssetsUnderManagement() - this method will be called when the Ivan's trade executes
    // - he can pass this method any/all information he has about that trade:
    //                    - the currency
    //                    - the exchanges
    //                    - the prices
    //                    - the buy/sell amounts


    public Transaction recalculateBalance(Exchange exchange, Currency currency, BigDecimal amount, String transactionType, String algorithm) {

        Transaction tldata = new Transaction();

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

            tldata.setBalance_before_transaction(BigDecimal.valueOf(0));

        } else {
            tldata.setBalance_before_transaction(balanceBefore);

        }

        BigDecimal balanceAfterTransaction = balanceBefore.subtract(feeAmount);

        tldata.setCurrency_pair_id(currencyId);
        tldata.setExchange_id(exchangeId);
        tldata.setTransaction_type(transactionType);
        tldata.setTransaction_amount(amount.subtract(fee));
        tldata.setTransaction_fee(feeAmount);
        tldata.setAlgorithm(algorithm);
        tldata.setBalance_after_transaction(balanceAfterTransaction);

        /* recalculate balance after buy/sell transaction
                check balance on the exchange wallet of the selected currency
                total1 = select total where exchange_id = exchange_name.getExchange_id and currency_id = currency_pair_id.getSymbol1
                total2 = select total where exchange_id = exchange_name.getExchange_id and currency_id = currency_pair_id.getSymbol2
                newTotal1 = total1 - sellAmount
                newTotal2 = total2 + buyAmount
            */
        return tldata;
    }


    public Transaction updateBalance(Exchange[] exchange, Currency currency, BigDecimal amount, String transactionType, String algorithm){

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
}
