package pe.client.custom.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoClientsPresentException extends RuntimeException {
    public NoClientsPresentException(String message) {
        super(message);
    }
}
