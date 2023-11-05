package az.ingress.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String code, String message) {
        super(message);
    }
}
