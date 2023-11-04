package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.consts.Role;
import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.Book;
import az.ingress.bookstore.dao.repo.AuthorRepository;
import az.ingress.bookstore.dao.repo.BookRepository;
import az.ingress.bookstore.dto.request.AuthorRequest;
import az.ingress.bookstore.dto.request.BookRequest;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.response.AuthorResponse;
import az.ingress.bookstore.dto.response.BookResponse;
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

import java.util.Objects;

import static az.ingress.bookstore.mapper.AuthorMapper.AUTHOR_MAPPER;
import static az.ingress.bookstore.mapper.BookMapper.BOOK_MAPPER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             PasswordEncoder passwordEncoder,
                             EncryptionService encryptionService,
                             BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
        this.encryptionService = encryptionService;
        this.bookRepository = bookRepository;
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
        if (author.getRole() == Role.AUTHOR) {
            Author updatedAuthor = AUTHOR_MAPPER.fromRequestToModel(authorRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(AUTHOR_MAPPER.fromModelToResponse1(authorRepository.save(updatedAuthor)));
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

    public ResponseEntity<BookResponse> createBook(BookRequest bookRequest) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByUsername(contextHolder.getName()).get();
        if (author.getRole() == Role.AUTHOR) {
            Book book = BOOK_MAPPER.fromRequestToModel(bookRequest);
            book.setAuthor(author);
            book.setAuthorName(author.getName());
            bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(BOOK_MAPPER.fromModelToResponse(book));
        } else return ResponseEntity.status(BAD_REQUEST).build();
    }

}