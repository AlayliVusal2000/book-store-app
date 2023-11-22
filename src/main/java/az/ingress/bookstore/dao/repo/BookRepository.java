package az.ingress.bookstore.dao.repo;

import az.ingress.bookstore.dao.entity.Book;
import az.ingress.bookstore.wrapper.BookWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByName(String name);
    List<Object[]> findBooksByStatus(@Param("studentId") Long studentId);

    List<BookWrapper> getAllBooks();
    List<BookWrapper> getAllBooksStatus();
}
