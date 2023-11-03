package az.ingress.bookstore.service;

import az.ingress.bookstore.dto.request.AuthenticationRequest;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthorAuthService {
    ResponseEntity<?> authorSignUp(RegisterRequest registerRequest);

    ResponseEntity<?> authorLogin(AuthenticationRequest request);
}
