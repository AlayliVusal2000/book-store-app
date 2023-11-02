package az.ingress.bookstore.dao.repo;

import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByUsername(String username);
}
