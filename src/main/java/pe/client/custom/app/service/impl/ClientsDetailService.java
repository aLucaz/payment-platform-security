package pe.client.custom.app.service.impl;

import jdk.nashorn.internal.runtime.Scope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Service;
import pe.client.custom.app.config.properties.ClientsDetail;
import pe.client.custom.app.exception.NoClientsPresentException;
import pe.client.custom.app.util.constant.Oauth;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class ClientsDetailService {

    private final ClientsDetail clientsDetail;

    public ClientsDetailService(ClientsDetail clientsDetail) {
        this.clientsDetail = clientsDetail;
    }

    public List<RegisteredClient> loadClients() throws NoClientsPresentException {
        List<RegisteredClient> registeredClients = new LinkedList<>();
        clientsDetail.getClientInformationList().forEach(clientInformation -> {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientInformation.getClientId())
                .clientSecret(Oauth.OAUTH_TOKEN_ALGORITH + clientInformation.getClientSecret())
                .tokenSettings(
                    TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(clientInformation.getAccessTokenValidity()))
                        .refreshTokenTimeToLive(Duration.ofSeconds(clientInformation.getRefreshTokenValidity()))
                        .build()
                )
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();
            for (String scope : clientInformation.getScopes()) {
                registeredClient = RegisteredClient.from(registeredClient)
                    .scope(scope)
                    .build();
            }
            registeredClients.add(registeredClient);
        });
        if (registeredClients.isEmpty()) {
            throw new NoClientsPresentException("No clients present on properties to configure");
        }
        return registeredClients;
    }
}
