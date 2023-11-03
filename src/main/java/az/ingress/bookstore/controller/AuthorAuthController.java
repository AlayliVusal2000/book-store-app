package az.ingress.bookstore.controller;

import az.ingress.bookstore.dto.request.AuthenticationRequest;
import az.ingress.bookstore.dto.request.RegisterRequest;
import az.ingress.bookstore.service.AuthorAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorAuthController {

    private final AuthorAuthService authorAuthService;

    public AuthorAuthController(AuthorAuthService authorAuthService) {
        this.authorAuthService = authorAuthService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegisterRequest request) {
        return authorAuthService.authorSignUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return authorAuthService.authorLogin(request);
    }
}