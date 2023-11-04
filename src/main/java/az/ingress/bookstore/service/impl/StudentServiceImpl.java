package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.consts.Role;
import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.dao.repo.BookRepository;
import az.ingress.bookstore.dao.repo.StudentRepository;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.StudentRequest;
import az.ingress.bookstore.dto.response.StudentResponse;
import az.ingress.bookstore.exception.IncorrectPasswordException;
import az.ingress.bookstore.exception.PasswordNotMatchesException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import az.ingress.bookstore.security.EncryptionService;
import az.ingress.bookstore.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static az.ingress.bookstore.mapper.StudentMapper.STUDENT_MAPPER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;

    public StudentServiceImpl(StudentRepository studentRepository,
                              PasswordEncoder passwordEncoder,
                              EncryptionService encryptionService,
                              BookRepository bookRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.encryptionService = encryptionService;
    }


    @Override
    public void changePassword(ChangePasswordRequest request) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            if (!encryptionService.verifyPassword(request.getOldPassword(), student.getPassword())) {
                throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.INCORRECT_PASSWORD);
            } else if (!Objects.equals(request.getNewPassword(), (request.getNewPasswordAgain()))) {
                throw new PasswordNotMatchesException(BAD_REQUEST.name(), ErrorMessage.PASSWORD_NOT_MATCHES);
            } else {
                String encodedPassword = passwordEncoder.encode(request.getNewPassword());
                student.setPassword(encodedPassword);
                studentRepository.save(student);
                log.info("Student password has been changed. ");
            }
        } else ResponseEntity.status(BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<StudentResponse> updateMyAccount(StudentRequest studentRequest) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            Student updatedStudent = STUDENT_MAPPER.fromRequestToModel(studentRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(STUDENT_MAPPER.fromModelToResponse1(studentRepository.save(updatedStudent)));
        } else return ResponseEntity.status(BAD_REQUEST).build();
    }

    @Override
    public void deleteMyAccount() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            studentRepository.delete(student);
            log.info("Account deleted. ");
        } else ResponseEntity.status(BAD_REQUEST).build();

    }

}