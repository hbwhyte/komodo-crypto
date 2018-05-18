package komodocrypto.configuration.exchange_utils;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        // Connect to Exchange
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey,secretKey);
        BinanceApiRestClient client = factory.newRestClient();

        // Test connection
        client.ping();

        return client;
    }

    /**
     * [NOT ACTIVE] Async Rest API: an asynchronous/non-blocking Binance API client
     *
     * @return BinanceApiAsyncRestClient object
     */
    public BinanceApiAsyncRestClient createAsyncExchange() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey,secretKey);
        BinanceApiAsyncRestClient client = factory.newAsyncRestClient();

        // Test connectivity
        client.ping(response -> System.out.println("Ping succeeded."));

        return client;
    }

    /**
     * [NOT ACTIVE] Websocket API: a data streaming client using Binance WebSocket API.
     * @return BinanceApiWebSocketClient
     */
    public BinanceApiWebSocketClient createStreamExchange() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey,secretKey);
        return factory.newWebSocketClient();
    }
}
