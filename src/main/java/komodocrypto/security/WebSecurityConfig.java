package komodocrypto.security;

import org.springframework.beans.factory.annotation.Autowired;
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

import javax.sql.DataSource;

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

    @Autowired
    DataSource dataSource;

    /**
     * Configure endpoints that require user authentication
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                // secure endpoints
                .antMatchers("/komodo/user").authenticated()

                // enable basic auth
                .and().httpBasic()

                // enable login
                .and().formLogin().loginPage("/komodo/home").defaultSuccessUrl("/komodo/user").permitAll()

                // enable logout
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/komodo/home").and().exceptionHandling();
    }

    /**
     * Configure users
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery("SELECT email as username, password, active FROM users WHERE email=?")
            .authoritiesByUsernameQuery("SELECT email, 'default' FROM users WHERE email=?");
    }

    /**
     * Encoder bean used for client secrets
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
