package az.ingress.bookstore.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String code, String message) {
        super(message);
    }
}
