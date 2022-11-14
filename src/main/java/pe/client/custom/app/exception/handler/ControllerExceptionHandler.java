package pe.client.custom.app.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.client.custom.app.exception.BadRequestException;
import pe.client.custom.app.exception.InternalServerException;
import pe.client.custom.app.exception.NoClientsPresentException;
import pe.client.custom.app.exception.UnauthorizedException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage badRequestException(BadRequestException ex) {
        return ErrorMessage.builder()
            .statusCode(HttpStatus.BAD_REQUEST)
            .userMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage internalServerException(InternalServerException ex) {
        return ErrorMessage.builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
            .userMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    }

    @ExceptionHandler(NoClientsPresentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage noClientsPresentException(NoClientsPresentException ex) {
        return ErrorMessage.builder()
            .statusCode(HttpStatus.NOT_FOUND)
            .userMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage unauthorizedException(UnauthorizedException ex) {
        return ErrorMessage.builder()
            .statusCode(HttpStatus.UNAUTHORIZED)
            .userMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    }

}
