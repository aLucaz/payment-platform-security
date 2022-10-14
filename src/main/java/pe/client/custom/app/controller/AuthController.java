package pe.client.custom.app.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.client.custom.app.dto.GetTokenResponseDto;
import pe.client.custom.app.exception.UnauthorizedException;
import pe.client.custom.app.service.impl.OAuth2ServiceImpl;
import pe.client.custom.app.util.constant.Header;
import pe.client.custom.app.util.constant.Param;

@RestController
@RequestMapping(path = "v1.0/payment/security")
public class AuthController {

    private final OAuth2ServiceImpl authService;

    public AuthController(OAuth2ServiceImpl authService) {
        this.authService = authService;
    }

    @GetMapping(
        path = "/token",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getToken(@RequestHeader(name = Header.X_AUTHORIZATION) String xAuthorization,
                                           @RequestParam(name = Param.GRANT_TYPE) String grantType,
                                           @RequestParam(name = Param.SCOPE) String scope) throws UnauthorizedException {
        GetTokenResponseDto response = authService.getToken(xAuthorization, grantType, scope);
        return ResponseEntity.ok(response);
    }
}
