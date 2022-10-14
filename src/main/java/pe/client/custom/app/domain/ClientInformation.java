package pe.client.custom.app.domain;

import lombok.Data;

import java.util.Set;

@Data
public class ClientInformation {
    private String clientId;
    private String clientSecret;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private Set<String> scopes;
}
