package pe.client.custom.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pe.client.custom.app.util.constant.Header;
import pe.client.custom.app.util.constant.Param;

@Data
public class GetTokenRequestDto {
    @JsonProperty(Header.X_AUTHORIZATION)
    private String authorization;
    @JsonProperty(Param.GRANT_TYPE)
    private String grantType;
    @JsonProperty(Param.SCOPE)
    private String scope;
    @JsonProperty(Header.CONTENT_TYPE)
    private String contentType;
}
