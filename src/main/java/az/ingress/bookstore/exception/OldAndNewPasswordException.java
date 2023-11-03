package az.ingress.bookstore.exception;

public class OldAndNewPasswordException extends RuntimeException {
    public OldAndNewPasswordException(String code, String message) {
        super(message);
    }
}
