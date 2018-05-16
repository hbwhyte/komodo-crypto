package komodocrypto.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.sql.DataSource;


/**
 * Configuration for Authorization Server
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * Configure client credentials
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients
                // gets client credentials from DB
                .jdbc(dataSource)

                // encode with BCrypt
                .passwordEncoder(passwordEncoder());
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
