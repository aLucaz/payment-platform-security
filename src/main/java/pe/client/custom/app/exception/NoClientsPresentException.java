package pe.client.custom.app.exception;

public class NoClientsPresentException extends RuntimeException {
    public NoClientsPresentException(String message) {
        super(message);
    }
}
