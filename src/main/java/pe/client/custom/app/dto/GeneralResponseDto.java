package pe.client.custom.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralResponseDto {
    private String message;
}
