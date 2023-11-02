package az.ingress.bookstore.service;

import az.ingress.bookstore.model.request.AuthenticationRequest;
import az.ingress.bookstore.model.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface StudentAuthService {
    ResponseEntity<?> studentSignUp(RegisterRequest registerRequest);

    ResponseEntity<?> studentLogin(AuthenticationRequest request);
}
