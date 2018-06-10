package komodocrypto.services.arbitrage;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.NewOrderResponseType;
import komodocrypto.configuration.exchange_utils.BinanceUtil;
import komodocrypto.configuration.exchange_utils.BitstampUtil;
import komodocrypto.exceptions.custom_exceptions.ExchangeConnectionException;
import komodocrypto.mappers.ArbitrageMapper;
import komodocrypto.model.arbitrage.ArbitrageModel;
import komodocrypto.model.arbitrage.ArbitrageOutput;
import komodocrypto.model.arbitrage.TradeDetails;
import komodocrypto.model.user.User;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.binance.api.client.domain.account.NewOrder.marketBuy;

@Service
public class ArbitrageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ArbitrageMapper arbitrageMapper;

    @Autowired
    BitstampUtil bitstampUtil;

    @Autowired
    BinanceUtil binanceUtil;

    public ArrayList<ArbitrageModel> getArbitrageData() {
        ArrayList<ArbitrageModel> ad = arbitrageMapper.getData();
        return ad;
    }

    //    creates a fake user so mvc views can work
    public User tempUser() {
        User user = new User();
        user.setFirst_name("Unicorn");
        user.setLast_name("Badger");
        user.setEmail("UnicornBadger@fake.com");
        user.setPassword("password");
        return user;
    }

    public ArbitrageOutput makeMarketTrade(String exchangeHigh, String exchangeLow, String currencyPair,
                                           BigDecimal amount) throws ExchangeConnectionException {
        // Set metadata
        ArbitrageOutput arbitrageTrade = new ArbitrageOutput();
        arbitrageTrade.setExchangeHigh(exchangeHigh);
        arbitrageTrade.setExchangeLow(exchangeLow);
        arbitrageTrade.setCurrencyPair(currencyPair);
        arbitrageTrade.setAmount(amount);

        // Create list of trade data
        List<TradeDetails> trades = new ArrayList<>();

        // Make high trade
        TradeDetails tradeHigh = tradeExchange(exchangeHigh, currencyPair, amount);

        // Swap currency pair order then make low trade
        String currencyPairLow = flipCurrencyPair(currencyPair);
        TradeDetails tradeLow = tradeExchange(exchangeLow,currencyPairLow,amount);

        trades.add(tradeHigh);
        trades.add(tradeLow);
        arbitrageTrade.setTradeDetails(trades);
        return arbitrageTrade;
    }

    private TradeDetails tradeExchange(String exchange, String currencyPair, BigDecimal amount)
            throws ExchangeConnectionException {
        switch (exchange.toLowerCase()) {
            case "bitstamp": {
                // Connect to Exchange
                Exchange bitstamp = bitstampUtil.createExchange();
                TradeService tradeService = bitstamp.getTradeService();
                TradeDetails trade = new TradeDetails();
                trade.setExchange("Bitstamp");

                // Place market order
                MarketOrder marketOrder = convertXchangeTrade(currencyPair, amount);
                logger.info("Attempting Bitstamp market order...");
                try {
                    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
                    trade.setTimestamp(System.currentTimeMillis());
                    trade.setOrderId(marketOrderReturnValue);
                    logger.info("Bitstamp order successfully placed.");
                } catch (IOException e) {
                    throw new ExchangeConnectionException("Unable to place order", HttpStatus.BAD_REQUEST);
                }
                return trade;
            }
            case "binance": {
                // Connect to Exchange
                BinanceApiRestClient client = binanceUtil.createExchange();
                TradeDetails trade = new TradeDetails();
                trade.setExchange("Binance");

                // Place market order
                logger.info("Attempting Binance market order...");
                NewOrderResponse newOrderResponse = client.newOrder(
                        marketBuy(currencyPair, amount.toString()).newOrderRespType(NewOrderResponseType.FULL));
                trade.setTimestamp(System.currentTimeMillis());
                trade.setOrderId(newOrderResponse.getClientOrderId());
                logger.info("Binance order successfully placed.");
                return trade;
            }

//            case "bittrex": {
//
//            }
//            case "kraken": {
//
//            }
//            case "gdax": {
//
//            }
            default:
                throw new ExchangeConnectionException("Invalid Exchange", HttpStatus.BAD_REQUEST);
        }

    }

    private MarketOrder convertXchangeTrade(String currencyPair, BigDecimal amount)
            throws ExchangeConnectionException {
        switch (currencyPair.toUpperCase()) {
            case "BTCETH": {
                return new MarketOrder(Order.OrderType.BID, amount, new CurrencyPair(Currency.BTC, Currency.ETH));
            }
            case "ETHBTC": {
                return new MarketOrder(Order.OrderType.ASK, amount, new CurrencyPair(Currency.ETH, Currency.BTC));
            }
            case "BTCLTC": {
                return new MarketOrder(Order.OrderType.BID, amount, new CurrencyPair(Currency.BTC, Currency.LTC));
            }
            case "LTCBTC": {
                return new MarketOrder(Order.OrderType.ASK, amount, new CurrencyPair(Currency.LTC, Currency.BTC));
            }
            case "BTCXRP": {
                return new MarketOrder(Order.OrderType.BID, amount, new CurrencyPair(Currency.BTC, Currency.XRP));
            }
            case "XRPBTC": {
                return new MarketOrder(Order.OrderType.ASK, amount, new CurrencyPair(Currency.XRP, Currency.BTC));
            }
            case "BTCBCH": {
                return new MarketOrder(Order.OrderType.BID, amount, new CurrencyPair(Currency.BTC, Currency.BCH));
            }
            case "BCHBTC": {
                return new MarketOrder(Order.OrderType.ASK, amount, new CurrencyPair(Currency.BCH, Currency.BTC));
            }
            default:
                throw new ExchangeConnectionException("Bad currency pair", HttpStatus.BAD_REQUEST);
        }
    }

    private String flipCurrencyPair(String currencyPair) {
     return currencyPair.substring(3) + currencyPair.substring(0,3);
    }
}
