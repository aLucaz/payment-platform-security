package pe.client.custom.app.service;

import pe.client.custom.app.dto.CheckTokenResponseDto;
import pe.client.custom.app.dto.GeneralResponseDto;
import pe.client.custom.app.dto.GetTokenResponseDto;
import pe.client.custom.app.exception.InternalServerException;
import pe.client.custom.app.exception.UnauthorizedException;

public interface OAuthService {
    GetTokenResponseDto getToken(String xAuthorization, String grantType, String scope) throws UnauthorizedException, InternalServerException;

    CheckTokenResponseDto checkToken(String xAuthorization, String token);

    GeneralResponseDto revokeToken(String xAuthorization, String token);
}
