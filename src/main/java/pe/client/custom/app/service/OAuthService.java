package pe.client.custom.app.service;

import pe.client.custom.app.exception.InternalServerException;
import pe.client.custom.app.exception.UnauthorizedException;

public interface OAuthService {
    Object getToken(String xAuthorization, String grantType, String scope) throws UnauthorizedException, InternalServerException;

    Object checkToken(String token);

    Object revokeToken(String xAuthorization, String xAuthToken);
}
