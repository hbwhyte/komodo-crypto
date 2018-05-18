package komodocrypto.configuration.exchange_utils;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BinanceUtil {

    @Value("${binance.apiKey}")
    private String apiKey;

    @Value("${binance.secretKey}")
    private String secretKey;

    /**
     * Rest API: a synchronous/blocking Binance API client
     *
     * @return BinanceApiRestClient object
     */
    public BinanceApiRestClient createExchange() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey,secretKey);
        return factory.newRestClient();
    }

    /**
     * Async Rest API: an asynchronous/non-blocking Binance API client
     *
     * @return BinanceApiAsyncRestClient object
     */
    public BinanceApiAsyncRestClient createAsyncExchange() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey,secretKey);
        return factory.newAsyncRestClient();
    }

    /**
     * Websocket API: a data streaming client using Binance WebSocket API.
     * @return BinanceApiWebSocketClient
     */
    public BinanceApiWebSocketClient createStreamExchange() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey,secretKey);
        return factory.newWebSocketClient();
    }

    public static void main(String[] args) {
        BinanceUtil obj = new BinanceUtil();
        BinanceApiAsyncRestClient client = obj.createAsyncExchange();

        // Test connectivity
        client.ping(response -> System.out.println("Ping succeeded."));

    }
}
