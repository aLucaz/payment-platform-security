package pe.client.custom.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CheckTokenResponseDto {
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("exp")
    private Long exp;
    @JsonProperty("authorities")
    private List<String> authorities;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("scope")
    private String scope;
}
