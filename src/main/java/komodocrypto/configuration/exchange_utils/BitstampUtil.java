package komodocrypto.configuration.exchange_utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;

public class BitstampUtil {

    public static Exchange createExchange() {

        ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
        // Put in your own information from Bitstamp here
        exSpec.setUserName("");
        exSpec.setApiKey("");
        exSpec.setSecretKey("");
        return ExchangeFactory.INSTANCE.createExchange(exSpec);
    }
}