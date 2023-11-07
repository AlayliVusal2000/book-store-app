package az.ingress.bookstore.dao.repo;

import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.wrapper.StudentWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);

    Student findByUsernameIgnoreCase(String username);
    List<StudentWrapper>getAllStudents();
}
