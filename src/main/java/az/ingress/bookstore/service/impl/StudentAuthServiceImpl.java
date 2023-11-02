package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.consts.Role;
import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.dao.repo.AuthorRepository;
import az.ingress.bookstore.dao.repo.StudentRepository;
import az.ingress.bookstore.exception.StudentNotFoundException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import az.ingress.bookstore.model.request.AuthenticationRequest;
import az.ingress.bookstore.model.request.RegisterRequest;
import az.ingress.bookstore.model.response.AuthenticationResponse;
import az.ingress.bookstore.security.EncryptionService;
import az.ingress.bookstore.security.JwtService;
import az.ingress.bookstore.service.StudentAuthService;
import az.ingress.bookstore.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static az.ingress.bookstore.exception.error.ErrorMessage.BAD_CREDENTIALS;
import static az.ingress.bookstore.exception.error.ErrorMessage.USERNAME_ALREADY_EXISTS;
import static az.ingress.bookstore.mapper.StudentMapper.STUDENT_MAPPER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@Service
@Slf4j
public class StudentAuthServiceImpl implements StudentAuthService {
    private final StudentRepository studentRepository;
    private final AuthorRepository authorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;

    public StudentAuthServiceImpl(AuthorRepository authorRepository,
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
    public ResponseEntity<?> studentSignUp(RegisterRequest registerRequest) {
        Optional<Author> author = authorRepository.findByUsername(registerRequest.getUsername());
        if (author.isEmpty()) {
            if (Util.validationSignUp(registerRequest)) {
                Optional<Student> optionalStudent = studentRepository.findByUsername(registerRequest.getUsername());
                if (optionalStudent.isEmpty()) {
                    Student student = STUDENT_MAPPER.fromRequestToModel(registerRequest);
                    student.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                    student.setRole(Role.STUDENT);
                    studentRepository.save(student);
                    return ResponseEntity.status(CREATED)
                            .body(STUDENT_MAPPER.fromModelToResponse(student));
                } else {
                    log.error("registerRequest {}", registerRequest);
                    return ResponseEntity.status(BAD_REQUEST).body(ErrorMessage.USERNAME_ALREADY_EXISTS);
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.status(BAD_REQUEST).body(USERNAME_ALREADY_EXISTS);
    }

    @Override
    public ResponseEntity<?> studentLogin(AuthenticationRequest request) {
        String jwt = checkJwt(request);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT);
        } else {
            studentRepository.findByUsername(request.getUsername()).orElseThrow(
                    () -> new StudentNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.AUTHOR_NOT_FOUND));
            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(jwt);
            return ResponseEntity.ok(response);
        }
    }

    public String checkJwt(AuthenticationRequest request) {
        Optional<Student> optionalStudent = studentRepository.findByUsername(request.getUsername());
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            if (encryptionService.verifyPassword(request.getPassword(), student.getPassword())) {
                return jwtService.generateToken(student.getUsername());
            } else return null;
        }
        return BAD_CREDENTIALS;
    }

}
