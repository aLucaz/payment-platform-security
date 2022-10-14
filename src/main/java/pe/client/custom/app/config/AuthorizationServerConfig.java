package pe.client.custom.app.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pe.client.custom.app.config.keys.KeyManager;
import pe.client.custom.app.config.properties.CustomTokenEnhancer;
import pe.client.custom.app.exception.NoClientsPresentException;
import pe.client.custom.app.service.impl.ClientsDetailService;

import java.util.List;

@Slf4j
@Configuration
public class AuthorizationServerConfig {

    private final KeyManager keyManager;
    private final ClientsDetailService clientsDetailService;
    private final CustomTokenEnhancer customTokenEnhancer;

    public AuthorizationServerConfig(KeyManager keyManager, ClientsDetailService clientsDetailService, CustomTokenEnhancer customTokenEnhancer) {
        this.keyManager = keyManager;
        this.clientsDetailService = clientsDetailService;
        this.customTokenEnhancer = customTokenEnhancer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() throws NoClientsPresentException {
        List<RegisteredClient> registeredClientList = this.clientsDetailService.loadClients();
        return new InMemoryRegisteredClientRepository(registeredClientList);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return customTokenEnhancer.enhance();
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
            .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet set = new JWKSet(keyManager.generateRSAKey());
        return (j, sc) -> j.select(set);
    }

}
