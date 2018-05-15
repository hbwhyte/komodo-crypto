package komodocrypto.configuration.exchange_utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class BitstampUtil {

    @Value("${bitstamp.username}")
    private String username;

    @Value("${bitstamp.apiKey}")
    private String apiKey;

    @Value("${bitstamp.secretKey}")
    private String secretKey;

    public Exchange createExchange() {

        ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
        // Put in your own information from Bitstamp here
        exSpec.setUserName(username);
        exSpec.setApiKey(apiKey);
        exSpec.setSecretKey(secretKey);
        return ExchangeFactory.INSTANCE.createExchange(exSpec);
    }
}