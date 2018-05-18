package komodocrypto.configuration.exchange_utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class BittrexUtil {

    @Value("${bittrex.username}")
    private String username;

    @Value("${bittrex.apiKey}")
    private String apiKey;

    @Value("${bittrex.secretKey}")
    private String secretKey;

    public static Exchange createExchange() {
        String username = "kanjtrader@gmail.com";
        String apiKey = "2affb155c90e4e648d5dad7baa853dc4";
        String secretKey = "fe6354bd79464e07812ed76204d6bfab";
        ExchangeSpecification exSpec = new BittrexExchange().getDefaultExchangeSpecification();
        // Put in your own information from Bittrex here
        exSpec.setUserName(username);
        exSpec.setApiKey(apiKey);
        exSpec.setSecretKey(secretKey);
        return ExchangeFactory.INSTANCE.createExchange(exSpec);

    }
}
