package pe.client.custom.app.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    protected HttpStatus statusCode;
    protected String errorCode;
    protected String systemMessage;
    protected String userMessage;
    protected LocalDateTime timestamp;
    protected String description;
}
