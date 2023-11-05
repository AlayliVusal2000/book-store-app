package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.consts.Role;
import az.ingress.bookstore.consts.Status;
import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.Book;
import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.dao.entity.Subscriber;
import az.ingress.bookstore.dao.repo.AuthorRepository;
import az.ingress.bookstore.dao.repo.BookRepository;
import az.ingress.bookstore.dao.repo.StudentRepository;
import az.ingress.bookstore.dao.repo.SubscriberRepository;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.StudentRequest;
import az.ingress.bookstore.dto.response.BookResponse;
import az.ingress.bookstore.dto.response.StudentResponse;
import az.ingress.bookstore.exception.AuthorNotFoundException;
import az.ingress.bookstore.exception.BookNotFoundException;
import az.ingress.bookstore.exception.IncorrectPasswordException;
import az.ingress.bookstore.exception.PasswordNotMatchesException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import az.ingress.bookstore.security.EncryptionService;
import az.ingress.bookstore.service.StudentService;
import az.ingress.bookstore.wrapper.BookWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static az.ingress.bookstore.mapper.BookMapper.BOOK_MAPPER;
import static az.ingress.bookstore.mapper.StudentMapper.STUDENT_MAPPER;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;
    private final BookRepository bookRepository;
    private final SubscriberRepository subscriberRepository;
    private final AuthorRepository authorRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              PasswordEncoder passwordEncoder,
                              EncryptionService encryptionService,
                              BookRepository bookRepository,
                              SubscriberRepository subscriberRepository,
                              AuthorRepository authorRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.encryptionService = encryptionService;
        this.bookRepository = bookRepository;
        this.subscriberRepository = subscriberRepository;
        this.authorRepository = authorRepository;
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
        } else ResponseEntity.status(FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<StudentResponse> updateMyAccount(StudentRequest studentRequest) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            student.setAge(studentRequest.getAge());
            student.setName(studentRequest.getName());
            student.setSurname(studentRequest.getSurname());
            StudentResponse response = STUDENT_MAPPER.fromModelToResponse(studentRepository.save(student));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        } else return ResponseEntity.status(FORBIDDEN).build();
    }

    @Override
    public void deleteMyAccount() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            studentRepository.delete(student);
            log.info("Account deleted. ");
        } else ResponseEntity.status(FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<BookResponse> takeBook(String bookName) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            Book book = bookRepository.findByName(bookName).orElseThrow(
                    () -> new BookNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.BOOK_NOT_FOUND));
            if (book.getStatus() != Status.NO) {
                book.setStatus(Status.NO);
                book.setStudent(student);
                bookRepository.save(book);
                return ResponseEntity.status(OK).body(BOOK_MAPPER.fromModelToResponse(book));
            }
            return ResponseEntity.status(BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<BookResponse> returnBook(String bookName) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            Book book = bookRepository.findByName(bookName).orElseThrow(
                    () -> new BookNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.BOOK_NOT_FOUND));
            book.setStatus(Status.HAVE);
            book.setStudent(null);
            bookRepository.save(book);
            return ResponseEntity.status(OK).body(BOOK_MAPPER.fromModelToResponse(book));
        }
        return ResponseEntity.status(BAD_REQUEST).build();
    }

    @Override
    public void subscribe(String authorName) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            Author author = authorRepository.findByUsername(authorName).orElseThrow(
                    () -> new AuthorNotFoundException(NOT_FOUND.name(), ErrorMessage.AUTHOR_NOT_FOUND));
            Subscriber subscriber = new Subscriber();
            subscriber.setAuthor(author);
            subscriber.setStudent(student);
            subscriberRepository.save(subscriber);
        }
    }

    @Override
    public List<Object[]> getMySubscriptions() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            return subscriberRepository.findStudentAndAuthorDetail(student.getId());
        } else ResponseEntity.status(FORBIDDEN).build();

        return null;
    }

    @Override
    public List<Object[]> readingBook() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByUsername(contextHolder.getName()).get();
        if (student.getRole() == Role.STUDENT) {
            return bookRepository.findBooksByStatus(student.getId());

        } else ResponseEntity.status(FORBIDDEN).build();

        return null;
    }

    @Override
    public List<BookWrapper> getAllBooks() {
        return bookRepository.getAllBooks();
    }

}