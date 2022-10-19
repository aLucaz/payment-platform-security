package pe.client.custom.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.client.custom.app.dto.CheckTokenResponseDto;
import pe.client.custom.app.dto.GeneralResponseDto;
import pe.client.custom.app.dto.GetTokenResponseDto;
import pe.client.custom.app.service.impl.OAuth2ServiceImpl;
import pe.client.custom.app.util.constant.Api;
import pe.client.custom.app.util.constant.Header;
import pe.client.custom.app.util.constant.Param;

@RestController
@RequestMapping(path = Api.API_BASE_PATH)
public class AuthController {

    private final OAuth2ServiceImpl authService;

    public AuthController(OAuth2ServiceImpl authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Obtener un JWT token",
        description = "Api que permite obtener un token bajo el flujo client_credential",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                        implementation = GetTokenResponseDto.class
                    ),
                    examples = {
                        @ExampleObject(
                            value = """
                                {
                                    "access_token": "eyJraWQiOiIyMGRmNjM3ZS03YTYxLTRiM2UtOTVkMC02YThjMjMwNWMzZmQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJBUlRDTElFTlQiLCJhdWQiOiJBUlRDTElFTlQiLCJuYmYiOjE2NjYxMTU4MjYsInNjb3BlIjpbIlJFQUQiXSwiaXNzIjoiaHR0cDpcL1wvbG9jYWxob3N0OjgwODUiLCJleHAiOjE2NjYxMTcwMjYsImlhdCI6MTY2NjExNTgyNiwiYXV0aG9yaXRpZXMiOltdfQ.bOWY_cYsiqUhBx8YHtwwJjymG3fbqEMjLdDq7OUJ_p-Rawv08RjmHwVC9JlDSA1yexrXdjkI_YLld8pmok7vqMYMFdepqOquNf-iQLyFqEPHUKH5PDjGPow-uoHQqF4aEOH4_qratAKjAY1vSOOwj98zMcyffNjqj7p3rPpRzYJLnCTfcva5Y55opYAyqmlcby3wdIetgyAw8ntjphY2o9IempNMFuXLeghaKO-ihCqTUi2pLABAACP3wVObWdZCdg6caT2Fri0E6PvIGLRVgSEhreRFNxitu9eybTc0Ku7-Gh8SlmC4UZeSR__jP_jvLHYQm_Vif3Ll28vIjMwX9A",
                                    "token_type": "Bearer",
                                    "expires_in": 1200
                                }
                                """
                        )
                    }
                )
            )
        }
    )
    @GetMapping(
        path = Api.API_GET_TOKEN,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getToken(@RequestHeader(name = Header.X_AUTHORIZATION) String xAuthorization,
                                           @RequestParam(name = Param.GRANT_TYPE) String grantType,
                                           @RequestParam(name = Param.SCOPE) String scope) {
        GetTokenResponseDto response = authService.getToken(xAuthorization, grantType, scope);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Validar un JWT token",
        description = "Api que permite obtener informacion de un token para saber si es valido",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                        implementation = CheckTokenResponseDto.class
                    ),
                    examples = {
                        @ExampleObject(
                            value = """
                                {
                                    "active": true,
                                    "exp": 1666123875,
                                    "authorities": [],
                                    "client_id": "ARTCLIENT",
                                    "scope": "READ"
                                }
                                """
                        )
                    }
                )
            )
        }
    )
    @PostMapping(
        path = Api.API_CHECK_TOKEN,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> checkToken(@RequestHeader(name = Header.X_AUTHORIZATION) String xAuthorization,
                                             @RequestParam(name = Param.TOKEN) String token) {
        CheckTokenResponseDto response = authService.checkToken(xAuthorization, token);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Remover un JWT token",
        description = "Api que permite eliminar la validez de un token",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                        implementation = GeneralResponseDto.class
                    ),
                    examples = {
                        @ExampleObject(
                            value = """
                                {
                                    "message": "Token revoked successfully"
                                }
                                """
                        )
                    }
                )
            )
        }
    )
    @DeleteMapping(
        path = Api.API_REVOKE_TOKEN,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> revokeToken(@RequestHeader(name = Header.X_AUTHORIZATION) String xAuthorization,
                                              @RequestParam(name = Param.TOKEN) String token) {
        GeneralResponseDto response = authService.revokeToken(xAuthorization, token);
        return ResponseEntity.ok(response);
    }
}
