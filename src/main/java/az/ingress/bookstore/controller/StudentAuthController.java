package az.ingress.bookstore.controller;

import az.ingress.bookstore.dto.request.AuthenticationRequest;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.RegisterRequest;
import az.ingress.bookstore.service.StudentAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;

@RestController
@RequestMapping("/student")
public class StudentAuthController {

    private final StudentAuthService studentAuthService;

    public StudentAuthController(StudentAuthService studentAuthService) {
        this.studentAuthService = studentAuthService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegisterRequest request) {
        return studentAuthService.studentSignUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return studentAuthService.studentLogin(request);
    }
    @PatchMapping("/changePassword")
    public ResponseEntity<String>changePassword(@RequestBody ChangePasswordRequest request){
        studentAuthService.changePassword(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
