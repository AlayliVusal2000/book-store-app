package az.ingress.bookstore.service;

import az.ingress.bookstore.dto.request.AuthorRequest;
import az.ingress.bookstore.dto.request.BookRequest;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.response.AuthorResponse;
import az.ingress.bookstore.dto.response.BookResponse;
import az.ingress.bookstore.wrapper.BookWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    void changePassword(ChangePasswordRequest request);

    void deleteMyAccount();

    ResponseEntity<BookResponse> createBook(BookRequest request);

    ResponseEntity<AuthorResponse> updateMyAccount(AuthorRequest request);

    void deleteBook(String bookName);

    List<Object[]> getAllSubscribers();
    List<BookResponse>getAllBooks();
}
