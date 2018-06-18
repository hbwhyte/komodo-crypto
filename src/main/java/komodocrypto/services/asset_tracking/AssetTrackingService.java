package komodocrypto.services.asset_tracking;

import komodocrypto.mappers.database.ArbitrageTradeHistoryMapper;
import komodocrypto.mappers.database.GroupPortfolioMapper;
import komodocrypto.mappers.database.TransactionMapper;
import komodocrypto.mappers.exchanges.ExchangeWalletMapper;
import komodocrypto.model.database.Currency;
import komodocrypto.model.database.CurrencyPairs;
import komodocrypto.model.database.Exchange;
import komodocrypto.model.database.Transaction;
import komodocrypto.model.exchanges.ExchangeWallet;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class AssetTrackingService {

    @Autowired
    GroupPortfolioMapper groupPortfolioMapper;

    @Autowired
    ArbitrageTradeHistoryMapper arbitrageTradeHistoryMapper;

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    ExchangeWalletMapper exchangeWalletMapper;


    //updateAssetsUnderManagement() - this method will be called when the Ivan's trade executes
    // - he can pass this method any/all information he has about that trade:
    //                    - the currency
    //                    - the exchanges
    //                    - the prices
    //                    - the buy/sell amounts

    public void mockBuy(Exchange exchange, CurrencyPairs currencyPairs, BigDecimal price, BigDecimal amount, String algorithm, String depositAddress){
        Transaction tldata = new Transaction();

        BigDecimal buyFee = exchange.getBuy_fee().multiply(amount).multiply(price);
        BigDecimal buyAmount = amount.multiply(price).subtract(buyFee);

        tldata.setExchange_id(exchange.getExchange_id());
        tldata.setCurrency_pair_id(currencyPairs.getCurrencyPairId());
        tldata.setAlgorithm(algorithm);
        tldata.setTransaction_amount(amount);
        tldata.setTransaction_fee(buyFee);
        tldata.setTransaction_type("Buy");

        transactionMapper.insertNewData(tldata);

        Currency currency1 = new Currency();
        currency1.setSymbol(currencyPairs.getSymbol1());
        Currency currency2 = new Currency();
        currency2.setSymbol(currencyPairs.getSymbol2());

        BigDecimal prevBalance1 = exchangeWalletMapper.getLatestAvailableBalance(currency1.getCurrency_id(), exchange.getExchange_id(), depositAddress);
        BigDecimal prevBalance2 = exchangeWalletMapper.getLatestAvailableBalance(currency2.getCurrency_id(), exchange.getExchange_id(), depositAddress);

        BigDecimal newBalance1 = prevBalance1.add(amount);
        BigDecimal newBalance2 = prevBalance2.subtract(buyAmount);

        ExchangeWallet wallet1 = new ExchangeWallet();
        wallet1.setAvailable(newBalance1);
        wallet1.setDepositAddress(depositAddress);
        exchangeWalletMapper.insertNewData(wallet1);

        ExchangeWallet wallet2 = new ExchangeWallet();
        wallet2.setAvailable(newBalance2);
        wallet2.setDepositAddress(depositAddress);
        exchangeWalletMapper.insertNewData(wallet2);

    }

    public void mockSell(Exchange exchange, CurrencyPairs currencyPairs, BigDecimal price, BigDecimal amount, String algorithm, String depositAddress){
        Transaction tldata = new Transaction();

        BigDecimal sellFee = exchange.getSell_fee().multiply(amount).multiply(price);
        BigDecimal sellAmount = amount.multiply(price).add(sellFee);

        tldata.setExchange_id(exchange.getExchange_id());
        tldata.setCurrency_pair_id(currencyPairs.getCurrencyPairId());
        tldata.setAlgorithm(algorithm);
        tldata.setTransaction_amount(amount);
        tldata.setTransaction_fee(sellFee);
        tldata.setTransaction_type("Sell");

        transactionMapper.insertNewData(tldata);

        Currency currency1 = new Currency();
        currency1.setSymbol(currencyPairs.getSymbol1());
        Currency currency2 = new Currency();
        currency2.setSymbol(currencyPairs.getSymbol2());

        BigDecimal prevBalance1 = exchangeWalletMapper.getLatestAvailableBalance(currency1.getCurrency_id(), exchange.getExchange_id(), depositAddress);
        BigDecimal prevBalance2 = exchangeWalletMapper.getLatestAvailableBalance(currency2.getCurrency_id(), exchange.getExchange_id(), depositAddress);

        BigDecimal newBalance1 = prevBalance1.subtract(sellAmount);
        BigDecimal newBalance2 = prevBalance2.add(amount);

        ExchangeWallet wallet1 = new ExchangeWallet();
        wallet1.setAvailable(newBalance1);
        wallet1.setDepositAddress(depositAddress);
        exchangeWalletMapper.insertNewData(wallet1);

        ExchangeWallet wallet2 = new ExchangeWallet();
        wallet2.setAvailable(newBalance2);
        wallet2.setDepositAddress(depositAddress);
        exchangeWalletMapper.insertNewData(wallet2);

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
