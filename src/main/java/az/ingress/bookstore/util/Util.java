package az.ingress.bookstore.util;

import az.ingress.bookstore.dto.request.RegisterRequest;

public class Util {
    public static boolean validationSignUp(RegisterRequest request) {
        return request.getName() != null && request.getUsername() != null && request.getPassword() != null;
    }

}
