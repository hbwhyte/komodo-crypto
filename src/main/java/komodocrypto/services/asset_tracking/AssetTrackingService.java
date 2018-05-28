package komodocrypto.services.asset_tracking;

import komodocrypto.mappers.GroupPortfolioMapper;
import komodocrypto.model.database.Exchange;
import komodocrypto.model.database.GroupPortfolio;
import org.springframework.beans.factory.annotation.Autowired;

public class AssetTrackingService {

    @Autowired
    GroupPortfolioMapper groupPortfolioMapper;

    //updateAssetsUnderManagement() - this method will be called when the Ivan's trade executes
    // - he can pass this method any/all information he has about that trade:
    //                    - the currency
    //                    - the exchanges
    //                    - the prices
    //                    - the buy/sell amounts

    public void recalculateBalance(int currency_pair_id, Exchange exchange, String transactionType){

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

    }

    public void updateAssetsUnderManagement(int buy_exchange_id, int sell_exchange_id, Exchange exchange_name,
                                            double price, double amount, int signal, String status, int currency_pair_id){

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
