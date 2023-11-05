package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.consts.Role;
import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.dao.repo.AuthorRepository;
import az.ingress.bookstore.dao.repo.StudentRepository;
import az.ingress.bookstore.dto.request.AuthenticationRequest;
import az.ingress.bookstore.dto.request.RegisterRequest;
import az.ingress.bookstore.dto.response.AuthenticationResponse;
import az.ingress.bookstore.exception.AuthorNotFoundException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import az.ingress.bookstore.security.EncryptionService;
import az.ingress.bookstore.security.jwt.JwtService;
import az.ingress.bookstore.service.AuthorAuthService;
import az.ingress.bookstore.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static az.ingress.bookstore.exception.error.ErrorMessage.BAD_CREDENTIALS;
import static az.ingress.bookstore.exception.error.ErrorMessage.USERNAME_ALREADY_EXISTS;
import static az.ingress.bookstore.mapper.AuthorMapper.AUTHOR_MAPPER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@Service
@Slf4j
public class AuthorAuthServiceImpl implements AuthorAuthService {
    private final StudentRepository studentRepository;
    private final AuthorRepository authorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;

    public AuthorAuthServiceImpl(AuthorRepository authorRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 EncryptionService encryptionService,
                                 StudentRepository studentRepository) {
        this.authorRepository = authorRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.encryptionService = encryptionService;
        this.studentRepository = studentRepository;
    }

    @Override
    public ResponseEntity<?> authorSignUp(RegisterRequest registerRequest) {
        Optional<Student> student = studentRepository.findByUsername(registerRequest.getUsername());
        if (student.isEmpty()) {
            if (Util.validationSignUp(registerRequest)) {
                Optional<Author> optionalAuthor = authorRepository.findByUsername(registerRequest.getUsername());
                if (optionalAuthor.isEmpty()) {
                    Author author = AUTHOR_MAPPER.fromRequestToModel(registerRequest);
                    author.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                    author.setRole(Role.AUTHOR);
                    authorRepository.save(author);
                    return ResponseEntity.status(CREATED)
                            .body(AUTHOR_MAPPER.fromModelToRegisterResponse(author));
                } else {
                    log.error("registerRequest {}", registerRequest);
                    return ResponseEntity.status(BAD_REQUEST).body(USERNAME_ALREADY_EXISTS);
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.status(BAD_REQUEST).body(USERNAME_ALREADY_EXISTS);
    }

    @Override
    public ResponseEntity<?> authorLogin(AuthenticationRequest request) {
        String jwt = checkJwt(request);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT);
        } else {
            authorRepository.findByUsername(request.getUsername()).orElseThrow(
                    () -> new AuthorNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.AUTHOR_NOT_FOUND));
            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(jwt);
            return ResponseEntity.status(CREATED).body(response);
        }
    }

    public String checkJwt(AuthenticationRequest request) {
        Optional<Author> optionalAuthor = authorRepository.findByUsername(request.getUsername());
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            if (encryptionService.verifyPassword(request.getPassword(), author.getPassword())) {
                return jwtService.generateTokenTest(author.getUsername());
            } else return null;
        }
        return BAD_CREDENTIALS;
    }

}
