package az.ingress.bookstore.controller;

import az.ingress.bookstore.model.request.AuthenticationRequest;
import az.ingress.bookstore.model.request.RegisterRequest;
import az.ingress.bookstore.service.StudentAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentAuthController {

    private final StudentAuthService studentAuthService;

    public StudentAuthController(StudentAuthService studentAuthService) {
        this.studentAuthService = studentAuthService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegisterRequest request) {
        System.out.println("sd sd");
        return studentAuthService.studentSignUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return studentAuthService.studentLogin(request);
    }

}
