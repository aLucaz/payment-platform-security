package pe.client.custom.app.service;

import pe.client.custom.app.dto.CheckTokenResponseDto;
import pe.client.custom.app.dto.GeneralResponseDto;
import pe.client.custom.app.dto.GetTokenResponseDto;

public interface OAuthService {
    GetTokenResponseDto getToken(String xAuthorization, String grantType, String scope);

    CheckTokenResponseDto checkToken(String xAuthorization, String token);

    GeneralResponseDto revokeToken(String xAuthorization, String token);
}
