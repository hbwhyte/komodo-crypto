package komodocrypto.security.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Configuration for Resource Server Filter (Komodo API)
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    /**
     * Configure endpoints that require access token authorization
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            // disable cross site risk forgery (vulnerability for now)
            .csrf().disable()

            // determine security for endpoints
            .authorizeRequests()
                .antMatchers("/test-security").authenticated();
    }
}
