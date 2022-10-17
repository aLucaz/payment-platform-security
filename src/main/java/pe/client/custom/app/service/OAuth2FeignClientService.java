package pe.client.custom.app.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import pe.client.custom.app.dto.CheckTokenResponseDto;
import pe.client.custom.app.dto.GetTokenResponseDto;
import pe.client.custom.app.util.constant.Oauth;

import java.util.Map;

@FeignClient(
    name = "oauth2-feign-client-service",
    url = "${payment.platform.security.url}"
)
public interface OAuth2FeignClientService {

    @PostMapping(
        path = Oauth.OAUTH_DEFAULT_GET_TOKEN,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    GetTokenResponseDto getToken(@RequestParam Map<String, String> params, @RequestHeader Map<String, String> headers);

    @PostMapping(
        path = Oauth.OAUTH_DEFAULT_INTROSPECT_TOKEN,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    CheckTokenResponseDto introspectToken(@RequestParam Map<String, String> params, @RequestHeader Map<String, String> headers);

    @PostMapping(
        path = Oauth.OAUTH_DEFAULT_REVOKE_TOKEN,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    Object revokeToken(@RequestParam Map<String, String> params, @RequestHeader Map<String, String> headers);
}
