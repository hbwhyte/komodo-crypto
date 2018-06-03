package komodocrypto.services.arbitrage;

import komodocrypto.mappers.ArbitrageMapper;
import komodocrypto.model.arbitrage.ArbitrageModel;
import komodocrypto.model.arbitrage.ArbitrageOutput;
import komodocrypto.model.user.User;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class ArbitrageService {
    @Autowired
    ArbitrageMapper arbitrageMapper;

    public ArrayList<ArbitrageModel> getArbitrageData(){
      ArrayList<ArbitrageModel> ad = arbitrageMapper.getData();
      return ad;
    };

//    creates a fake user so mvc views can work
    public User tempUser(){
        User user = new User();
        user.setFirst_name("Unicorn");
        user.setLast_name("Badger");
        user.setEmail("UnicornBadger@fake.com");
        user.setPassword("password");
        return user;
    }

    public ArbitrageOutput makeMarketTrade(Exchange exchangeHigh, Exchange exchangeLow, CurrencyPair currencyPair, BigDecimal amount) {
        // TODO Sell on exchangeHigh for currencyPair
            // call method that matches Exchange param to trade method;
        // Buy on exchangeLow for currencyPair
        ArbitrageOutput trade = new ArbitrageOutput();
        return trade;
    }
}
