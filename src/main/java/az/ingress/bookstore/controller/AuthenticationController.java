package az.ingress.bookstore.controller;


import az.ingress.bookstore.dao.repo.UserRepository;
import az.ingress.bookstore.model.request.AuthenticationRequest;
import az.ingress.bookstore.model.request.RegisterRequest;
import az.ingress.bookstore.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegisterRequest request) {
        return authenticationService.signUp(request);
    }

    //    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
//        String jwt = authenticationService.checkJwt(request);
//        if (jwt == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT);
//        } else {
//            UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(
//                    () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
//            if (Objects.nonNull(user)) {
//                AuthenticationResponse response = new AuthenticationResponse();
//                response.setToken(String.valueOf(jwt));
//                return ResponseEntity.ok(response);
//            }
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USER_ALREADY_EXISTS);
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);

    }

}