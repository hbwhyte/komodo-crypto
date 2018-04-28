package komodocrypto.security.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


/**
 * Configuration for Authorization Server
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private static final int EXPIRATION = 3600;

    @Value("${komodo.client_id}")
    private String komodo_id;

    @Value("${komodo.client_secret}")
    private String komodo_secret;

    @Value("${external.client_id}")
    private String external_id;

    @Value("${external.client_secret}")
    private String external_secret;

    /**
     * Configures client credentials
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // client credentials for Komodo MVC
        clients.inMemory()

            // set client_id, encoded client_secret and token expiration
            .withClient(komodo_id).secret(passwordEncoder().encode(komodo_secret)).accessTokenValiditySeconds(EXPIRATION)

            // client has read/write access
            .scopes("read", "write")

            // allows client to obtain access tokens via user authentication and refresh token
            .authorizedGrantTypes("password", "refresh_token");

        // client credentials for 3rd Party Developer using Komodo API
        clients.inMemory()

            // set client_id, encoded client_secret and token expiration
            .withClient("third-party").secret(passwordEncoder().encode("temp-secret")).accessTokenValiditySeconds(EXPIRATION)
//            .autoApprove(true)

            // client has read only access
            .scopes("read", "write")

            // allows client to obtain access tokens via client credentials only
            .authorizedGrantTypes("client_credentials");
    }

    /**
     * Set password encoder for client secrets to bCrypt
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder());
    }

    /**
     * Encoder bean used for client secrets
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
