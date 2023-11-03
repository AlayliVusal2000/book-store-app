package az.ingress.bookstore.exception;

public class PasswordNotMatchesException extends RuntimeException {
    public PasswordNotMatchesException(String code, String message) {
        super(message);
    }
}
