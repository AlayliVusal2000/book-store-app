package az.ingress.bookstore.service;

import az.ingress.bookstore.model.request.AuthenticationRequest;
import az.ingress.bookstore.model.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseEntity<?> signUp(RegisterRequest registerRequest);

    ResponseEntity<?> login(AuthenticationRequest request);

    String checkJwt(AuthenticationRequest request);

    boolean validationSignUp(RegisterRequest userRequest);
}
