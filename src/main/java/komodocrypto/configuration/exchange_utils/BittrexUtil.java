package komodocrypto.configuration.exchange_utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public final class BittrexUtil {

    @Value("${bittrex.username}")
    private String username;

    @Value("${bittrex.apiKey}")
    private String apiKey;

    @Value("${bittrex.secretKey}")
    private String secretKey;

    public Exchange createExchange() {
//        String username = ;
//        String apiKey = ;
//        String secretKey = ;
        ExchangeSpecification exSpec = new BittrexExchange().getDefaultExchangeSpecification();
        // Put in your own information from Bittrex here
        exSpec.setUserName(username);
        exSpec.setApiKey(apiKey);
        exSpec.setSecretKey(secretKey);
        return ExchangeFactory.INSTANCE.createExchange(exSpec);

    }
}
