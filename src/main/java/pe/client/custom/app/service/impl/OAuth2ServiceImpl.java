package pe.client.custom.app.service.impl;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import pe.client.custom.app.config.properties.ClientsDetail;
import pe.client.custom.app.domain.ClientInformation;
import pe.client.custom.app.dto.CheckTokenResponseDto;
import pe.client.custom.app.dto.GeneralResponseDto;
import pe.client.custom.app.dto.GetTokenResponseDto;
import pe.client.custom.app.exception.BadRequestException;
import pe.client.custom.app.exception.InternalServerException;
import pe.client.custom.app.exception.UnauthorizedException;
import pe.client.custom.app.service.OAuth2FeignClientService;
import pe.client.custom.app.service.OAuthService;
import pe.client.custom.app.util.Decoder;
import pe.client.custom.app.util.constant.Header;
import pe.client.custom.app.util.constant.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OAuth2ServiceImpl implements OAuthService {

    private final ClientsDetail clientsDetail;
    private final OAuth2FeignClientService oAuth2FeignClientService;

    public OAuth2ServiceImpl(ClientsDetail clientsDetail, OAuth2FeignClientService oAuth2FeignClientService) {
        this.clientsDetail = clientsDetail;
        this.oAuth2FeignClientService = oAuth2FeignClientService;
    }

    public GetTokenResponseDto getToken(String xAuthorization, String grantType, String scope) throws UnauthorizedException, InternalServerException {
        if (invalidAuthorizationHeader(xAuthorization)) {
            throw new BadRequestException("Invalid authorization code");
        }
        Map<String, String> parameters = securityParameters(grantType, scope);
        Map<String, String> headers = securityHeaders(xAuthorization);
        try {
            GetTokenResponseDto getTokenResponse = oAuth2FeignClientService.getToken(parameters, headers);
            log.info("Token obtained correctly");
            return getTokenResponse;
        } catch (FeignException.BadRequest ex) {
            log.error("OAuth2 standard error: Bad Request");
            log.error(ex.getMessage(), ex);
            throw new BadRequestException("Invalid data on the request");
        } catch (Exception ex) {
            log.error("Unknown error ocurred");
            log.error(ex.getMessage(), ex);
            throw new InternalServerException("An Unknown error occurred while generating token");
        }
    }

    @Override
    public CheckTokenResponseDto checkToken(String xAuthorization, String token) {
        validateAuthorizationAndToken(xAuthorization, token);
        Map<String, String> parameters = tokenParameters(token);
        Map<String, String> headers = securityHeaders(xAuthorization);
        try {
            CheckTokenResponseDto checkTokenResponse = oAuth2FeignClientService.introspectToken(parameters, headers);
            log.info("Token validated correctly");
            return checkTokenResponse;
        } catch (FeignException.BadRequest ex) {
            log.error("OAuth2 standard error: Bad Request");
            log.error(ex.getMessage(), ex);
            throw new BadRequestException("Invalid data on the request");
        } catch (Exception ex) {
            log.error("Unknown error ocurred");
            log.error(ex.getMessage(), ex);
            throw new InternalServerException("An Unknown error occurred while validating token");
        }
    }

    @Override
    public GeneralResponseDto revokeToken(String xAuthorization, String token) {
        validateAuthorizationAndToken(xAuthorization, token);
        Map<String, String> parameters = tokenParameters(token);
        Map<String, String> headers = securityHeaders(xAuthorization);
        try {
            oAuth2FeignClientService.revokeToken(parameters, headers);
            log.info("Token revoked");
            return GeneralResponseDto.builder()
                .message("Token revoked successfully")
                .build();
        } catch (FeignException.BadRequest ex) {
            log.error("OAuth2 standard error: Bad Request");
            log.error(ex.getMessage(), ex);
            throw new BadRequestException("Invalid data on the request");
        } catch (Exception ex) {
            log.error("Unknown error ocurred");
            log.error(ex.getMessage(), ex);
            throw new InternalServerException("An Unknown error occurred while validating token");
        }
    }

    private void validateAuthorizationAndToken(String xAuthorization, String token) {
        if (token == null || token.isEmpty()) {
            throw new BadRequestException("Empty or null token is provided to the api");
        }
        if (invalidAuthorizationHeader(xAuthorization)) {
            throw new BadRequestException("Invalid authorization code");
        }
    }

    private Map<String, String> securityParameters(String grantType, String scope) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Param.GRANT_TYPE, grantType);
        parameters.put(Param.SCOPE, scope);
        return parameters;
    }

    private Map<String, String> securityHeaders(String xAuthorization) {
        Map<String, String> headers = new HashMap<>();
        headers.put(Header.X_AUTHORIZATION, xAuthorization);
        headers.put(Header.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return headers;
    }

    private Map<String, String> tokenParameters(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put(Param.TOKEN, token);
        return headers;
    }

    private boolean invalidAuthorizationHeader(String authorization) {
        String[] authInfo = authorization.split(Header.X_AUTHORIZATION_TYPE);
        String credential = authInfo[1].trim();
        credential = Decoder.decodeFromBase64(credential);
        int separatorIndex = credential.indexOf(Header.X_CREDENTIAL_SEPARATOR);
        String clientId = credential.substring(0, separatorIndex);
        String clientSecret = credential.substring(separatorIndex + 1);
        return !isValidClientIdAndSecret(clientId, clientSecret);
    }

    private boolean isValidClientIdAndSecret(String clientId, String clientSecret) {
        List<ClientInformation> filteredInformationList = this.clientsDetail
            .getClientInformationList()
            .stream()
            .filter(element -> element.getClientId().equals(clientId))
            .collect(Collectors.toList());
        if (filteredInformationList.isEmpty()) {
            return false;
        }
        ClientInformation client = filteredInformationList.get(0);
        return client.getClientSecret().equals(clientSecret);
    }
}
