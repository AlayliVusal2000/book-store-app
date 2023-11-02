package az.ingress.bookstore.service;

import az.ingress.bookstore.model.request.AuthenticationRequest;
import az.ingress.bookstore.model.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthorAuthService {
    ResponseEntity<?> authorSignUp(RegisterRequest registerRequest);

    ResponseEntity<?> authorLogin(AuthenticationRequest request);
}
