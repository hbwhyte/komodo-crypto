package komodocrypto.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration for User Filter (Komodo MVC)
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${komodo.username}")
    private String komodo_username;

    @Value("${komodo.password}")
    private String komodo_password;

    /**
     * Configure endpoints that require user authentication
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                // secure endpoints
                .antMatchers("/user-dashboard").authenticated()

                // enable basic auth
                .and().httpBasic()

                // enable login
                .and().formLogin().permitAll()

                // enable logout
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling();
    }

    /**
     * Configure users
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(komodo_username).password(passwordEncoder().encode(komodo_password)).roles("USER");
    }

    /**
     * Encoder bean used for client secrets
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
