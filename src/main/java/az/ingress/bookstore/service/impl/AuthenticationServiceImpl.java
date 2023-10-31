package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.dao.entity.UserEntity;
import az.ingress.bookstore.dao.repo.UserRepository;
import az.ingress.bookstore.exception.UserNotFoundException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import az.ingress.bookstore.model.request.AuthenticationRequest;
import az.ingress.bookstore.model.request.RegisterRequest;
import az.ingress.bookstore.model.response.AuthenticationResponse;
import az.ingress.bookstore.security.EncryptionService;
import az.ingress.bookstore.security.JwtService;
import az.ingress.bookstore.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static az.ingress.bookstore.exception.error.ErrorMessage.BAD_CREDENTIALS;
import static az.ingress.bookstore.mapper.UserMapper.INSTANCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     JwtService jwtService,
                                     PasswordEncoder passwordEncoder,
                                     EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.encryptionService = encryptionService;
    }

    @Override
    public ResponseEntity<?> signUp(RegisterRequest registerRequest) {
        if (validationSignUp(registerRequest)) {
            Optional<UserEntity> user = userRepository.findByUsername(registerRequest.getUsername());
            if (user.isEmpty()) {
                UserEntity userEntity = INSTANCE.fromRequestToModel(registerRequest);
                userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                userRepository.save(userEntity);
                return ResponseEntity.status(CREATED)
                        .body(INSTANCE.fromModelToResponse(userEntity));
            } else {
                log.error("registerRequest {}", registerRequest);
                return ResponseEntity.status(BAD_REQUEST).body(ErrorMessage.USER_ALREADY_EXISTS);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public String checkJwt(AuthenticationRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (encryptionService.verifyPassword(request.getPassword(), user.getPassword())) {
                return jwtService.generateToken(user);
            } else return null;
        }
        return BAD_CREDENTIALS;

    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest request) {
        String jwt = checkJwt(request);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT);
        } else {
            userRepository.findByUsername(request.getUsername()).orElseThrow(
                    () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(jwt);
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public boolean validationSignUp(RegisterRequest request) {
        var s=request.getName() != null && request.getEmail() != null
                && request.getUsername() != null && request.getPassword() != null;
        System.out.println(s);
        return request.getName() != null && request.getEmail() != null
                && request.getUsername() != null && request.getPassword() != null;
    }

}