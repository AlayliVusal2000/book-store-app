package az.ingress.bookstore.service;

import az.ingress.bookstore.dto.request.AuthenticationRequest;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface StudentAuthService {
    ResponseEntity<?> studentSignUp(RegisterRequest registerRequest);

    ResponseEntity<?> studentLogin(AuthenticationRequest request);
     void changePassword(ChangePasswordRequest request);
}
