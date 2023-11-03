package az.ingress.bookstore.dao.repo;

import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student ,Long> {
    Optional<Student> findByUsername(String username);
    Student findByUsernameIgnoreCase(String username);
}
