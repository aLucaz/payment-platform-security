package pe.client.custom.app.config.properties;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import pe.client.custom.app.util.constant.Oauth;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomTokenEnhancer {

    public OAuth2TokenCustomizer<JwtEncodingContext> enhance() {
        return context -> {
            if (context.getTokenType().getValue().equals(Oauth.OAUTH_TOKEN_TYPE)) {
                Authentication principal = context.getPrincipal();
                Set<String> authorities = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
                context
                    .getClaims()
                    .claim("authorities", authorities);
            }
        };
    }
}
