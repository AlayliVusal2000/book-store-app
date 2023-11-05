package az.ingress.bookstore.controller;

import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.StudentRequest;
import az.ingress.bookstore.dto.response.BookResponse;
import az.ingress.bookstore.dto.response.StudentResponse;
import az.ingress.bookstore.service.StudentService;
import az.ingress.bookstore.wrapper.BookWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        studentService.changePassword(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    public ResponseEntity<StudentResponse> updateMyAccount(@RequestBody StudentRequest request) {
        return studentService.updateMyAccount(request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMyAccount() {
        studentService.deleteMyAccount();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/take/{bookName}")
    public ResponseEntity<BookResponse> takeBook(@PathVariable String bookName) {
        return studentService.takeBook(bookName);
    }

    @PostMapping("/return/{bookName}")
    public ResponseEntity<BookResponse> returnBook(@PathVariable String bookName) {
        return studentService.returnBook(bookName);
    }

    @PostMapping("/subscribe/{authorName}")
    public void subscribe(@PathVariable String authorName) {
        studentService.subscribe(authorName);
    }

    @GetMapping("/getAll")
    public List<Object[]> getAllSubscriptions() {
        return studentService.getMySubscriptions();
    }

    @GetMapping("/getBook")
    public List<Object[]> getMyReadingBook() {
        return studentService.readingBook();
    }
    @GetMapping("/allBooks")
    public List<BookWrapper>getAllBooks(){
        return studentService.getAllBooks();
    }

}
