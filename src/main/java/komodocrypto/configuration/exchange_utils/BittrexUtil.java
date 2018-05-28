package komodocrypto.configuration.exchange_utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
public class BittrexUtil {

    @Value("${bittrex.username}")
    private String username;

    @Value("${bittrex.apiKey}")
    private String apiKey;

    @Value("${bittrex.secretKey}")
    private String secretKey;

    public Exchange createExchange() {
        ExchangeSpecification exSpec = new BittrexExchange().getDefaultExchangeSpecification();
        exSpec.setUserName(username);
        exSpec.setApiKey(apiKey);
        exSpec.setSecretKey(secretKey);
        return ExchangeFactory.INSTANCE.createExchange(exSpec);
    }
}
