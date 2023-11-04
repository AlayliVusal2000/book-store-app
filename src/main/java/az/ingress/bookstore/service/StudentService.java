package az.ingress.bookstore.service;

import az.ingress.bookstore.dto.request.ChangePasswordRequest;
import az.ingress.bookstore.dto.request.StudentRequest;
import az.ingress.bookstore.dto.response.StudentResponse;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    void changePassword(ChangePasswordRequest request);

    void deleteMyAccount();

    ResponseEntity<StudentResponse> updateMyAccount(StudentRequest request);
}
