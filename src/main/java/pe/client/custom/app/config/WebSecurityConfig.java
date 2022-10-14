package pe.client.custom.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import pe.client.custom.app.util.constant.Oauth;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeRequests()
            .mvcMatchers(Oauth.OAUTH_DEFAULT_GET_TOKEN).hasIpAddress(Oauth.OAUTH_IP)
            .mvcMatchers(HttpMethod.GET, "/v1.0/payment/security/token").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .build();
    }
}
