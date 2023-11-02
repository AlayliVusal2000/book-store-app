package az.ingress.bookstore.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String code, String message) {
        super(message);
    }

}