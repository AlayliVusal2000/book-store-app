package az.ingress.bookstore.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String code, String message) {
        super(message);
    }

}