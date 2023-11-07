package az.ingress.bookstore.service;

import az.ingress.bookstore.wrapper.StudentWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmailService {
     void sendEmailToStudents(List<StudentWrapper> students, String subject, String message);
}
