package komodocrypto.services.exchanges;

import komodocrypto.mappers.database.CurrencyPairsMapper;
import komodocrypto.mappers.exchanges.ExchangeInfoMapper;
import komodocrypto.model.database.CurrencyPairs;
import komodocrypto.model.exchanges.ExchangeInfo;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ExchangeService {

    @Autowired
    ExchangeInfoMapper exchangeInfoMapper;

    @Autowired
    CurrencyPairsMapper currencyPairsMapper;

    // Generates a list of exchanges from the database that the application is using.
    public ArrayList<Exchange> generateDefaultExchangeList() {

        // The list of Exchange objects to return
        ArrayList<Exchange> exchangesList = new ArrayList<>();

        // The list of exchanges from the database
        List<ExchangeInfo> exchangeInfos = new ArrayList<>();
        exchangeInfos = exchangeInfoMapper.getExchanges();

        // Tries to create a new Exchange object and add it to the list to be returned for each exchange found in the
        // database.
        for (ExchangeInfo ei : exchangeInfos) {

            Exchange exchange;
            String exchangeName = ei.getExchangeName();

            try {
                exchange = ExchangeFactory.INSTANCE.createExchange(exchangeName);
            } catch (Exception e) {
                exchange = null;
            }

            if (exchange != null) exchangesList.add(exchange);
        }

        return exchangesList;
    }

    // Generates a list of currency pairs from the database that the application is using.
    public ArrayList<CurrencyPair> generateCurrencyPairList() {

        // The list of CurrencyPair objects to return
        ArrayList<CurrencyPair> pairsList = new ArrayList<>();

        // The list of pairs in the database
        List<CurrencyPairs> currencyPairs = new ArrayList<>();
        currencyPairs = currencyPairsMapper.getAllCurrencyPairs();

        // Creates and adds a new CurrencyPair object to the list to be returned.
        for (CurrencyPairs cp : currencyPairs) {

            String base = cp.getSymbol1();
            String counter = cp.getSymbol2();
            CurrencyPair currencyPair = new CurrencyPair(base, counter);
            pairsList.add(currencyPair);
        }

        return pairsList;
    }
}
