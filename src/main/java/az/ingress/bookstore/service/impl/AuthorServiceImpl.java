package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.consts.Role;
import az.ingress.bookstore.consts.Status;
import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.Book;
import az.ingress.bookstore.dao.repo.AuthorRepository;
import az.ingress.bookstore.dao.repo.BookRepository;
import az.ingress.bookstore.dao.repo.SubscriberRepository;
import az.ingress.bookstore.dto.request.AuthorRequest;
import az.ingress.bookstore.dto.request.BookRequest;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.response.AuthorResponse;
import az.ingress.bookstore.dto.response.BookResponse;
import az.ingress.bookstore.exception.AuthorNotFoundException;
import az.ingress.bookstore.exception.BookNotFoundException;
import az.ingress.bookstore.exception.IncorrectPasswordException;
import az.ingress.bookstore.exception.PasswordNotMatchesException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import az.ingress.bookstore.security.EncryptionService;
import az.ingress.bookstore.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static az.ingress.bookstore.mapper.AuthorMapper.AUTHOR_MAPPER;
import static az.ingress.bookstore.mapper.BookMapper.BOOK_MAPPER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;
    private final BookRepository bookRepository;
    private final SubscriberRepository subscriberRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             PasswordEncoder passwordEncoder,
                             EncryptionService encryptionService,
                             BookRepository bookRepository,
                             SubscriberRepository subscriberRepository) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
        this.encryptionService = encryptionService;
        this.bookRepository = bookRepository;
        this.subscriberRepository = subscriberRepository;
    }


    @Override
    public void changePassword(ChangePasswordRequest request) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        if (author.getRole() == Role.AUTHOR) {
            if (!encryptionService.verifyPassword(request.getOldPassword(), author.getPassword())) {
                throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.INCORRECT_PASSWORD);
            } else if (!Objects.equals(request.getNewPassword(), (request.getNewPasswordAgain()))) {
                throw new PasswordNotMatchesException(BAD_REQUEST.name(), ErrorMessage.PASSWORD_NOT_MATCHES);
            } else {
                String encodedPassword = passwordEncoder.encode(request.getNewPassword());
                author.setPassword(encodedPassword);
                authorRepository.save(author);
                log.info("Author password has been changed. ");
            }
        } else ResponseEntity.status(BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<AuthorResponse> updateMyAccount(AuthorRequest authorRequest) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        authorRepository.findByUsername(authorRequest.getUsername()).orElseThrow(
                () -> new AuthorNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.AUTHOR_NOT_FOUND));
        if (author.getRole() == Role.AUTHOR) {
            author.setRole(Role.AUTHOR);
            author.setPassword(author.getPassword());
            author.setAge(authorRequest.getAge());
            author.setName(authorRequest.getName());
            author.setSurname(authorRequest.getSurname());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(AUTHOR_MAPPER.fromModelToResponse
                            (author));
        } else return ResponseEntity.status(BAD_REQUEST).build();
    }

    @Override
    public void deleteMyAccount() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        if (author.getRole() == Role.AUTHOR) {
            authorRepository.delete(author);
            log.info("Account deleted. ");
        } else ResponseEntity.status(BAD_REQUEST).build();

    }

    @Override
    public ResponseEntity<BookResponse> createBook(BookRequest bookRequest) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        if (author.getRole() == Role.AUTHOR) {
            Book book = BOOK_MAPPER.fromRequestToModel(bookRequest);
            book.setAuthor(author);
            book.setStatus(Status.HAVE);
            book.setAuthorName(author.getName());
            bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(BOOK_MAPPER.fromModelToResponse(book));
        } else return ResponseEntity.status(BAD_REQUEST).build();
    }

    @Override
    public void deleteBook(String bookName) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        if (author.getRole() == Role.AUTHOR) {
            Book book = bookRepository.findByName(bookName).orElseThrow(
                    () -> new BookNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.BOOK_NOT_FOUND));
            bookRepository.delete(book);
            log.info("A book named " + bookName + "has been deleted");
        } else ResponseEntity.status(FORBIDDEN).build();
    }

    @Override
    public List<Object[]> getAllSubscribers() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        if (author.getRole() == Role.AUTHOR) {
            return subscriberRepository.findStudentAndAuthorDetails(author.getId());
        } else ResponseEntity.status(FORBIDDEN).build();
        return null;
    }

    @Override
    public List<BookResponse> getAllBooks() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        if (author.getRole() != Role.AUTHOR) {
            ResponseEntity.status(FORBIDDEN).build();
        }
            List<Book> books = bookRepository.findAll();
            return BOOK_MAPPER.fromListModelToListResponse(books);

    }
}