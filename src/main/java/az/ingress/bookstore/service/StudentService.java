package az.ingress.bookstore.service;

import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.StudentRequest;
import az.ingress.bookstore.dto.response.BookResponse;
import az.ingress.bookstore.dto.response.StudentResponse;
import az.ingress.bookstore.exception.BookNotFoundException;
import az.ingress.bookstore.wrapper.BookWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    void changePassword(ChangePasswordRequest request);

    void deleteMyAccount();

    ResponseEntity<StudentResponse> updateMyAccount(StudentRequest request);
    List<BookWrapper>getAllBooks();

    ResponseEntity<BookResponse> takeBook(String bookName);

    ResponseEntity<BookResponse> returnBook(String bookName);

    void subscribe(String authorName);

    List<Object[]> getMySubscriptions();

    List<Object[]> readingBook();
}
