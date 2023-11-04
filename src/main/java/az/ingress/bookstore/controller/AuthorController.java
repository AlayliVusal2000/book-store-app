package az.ingress.bookstore.controller;

import az.ingress.bookstore.dto.request.AuthorRequest;
import az.ingress.bookstore.dto.request.BookRequest;
import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.response.AuthorResponse;
import az.ingress.bookstore.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        authorService.changePassword(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    public ResponseEntity<AuthorResponse> updateMyAccount(@RequestBody AuthorRequest request) {
        return authorService.updateMyAccount(request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMyAccount() {
        authorService.deleteMyAccount();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BookRequest bookRequest) {
        authorService.createBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
