package komodocrypto.configuration.exchange_utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KrakenUtil {

    @Value("${kraken.username}")
    private String username;

    @Value("${kraken.apiKey}")
    private String apiKey;

    @Value("${kraken.secretKey}")
    private String secretKey;

    public Exchange createExchange() {

        ExchangeSpecification exSpec = new KrakenExchange().getDefaultExchangeSpecification();
        // Put in your own information from Kraken here
        exSpec.setUserName(username);
        exSpec.setApiKey(apiKey);
        exSpec.setSecretKey(secretKey);
        return ExchangeFactory.INSTANCE.createExchange(exSpec);
    }
}


