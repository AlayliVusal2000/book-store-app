package az.ingress.bookstore.dao.entity;

import az.ingress.bookstore.consts.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@NamedQuery(name = "Book.getAllBooks",
        query = "select new az.ingress.bookstore.wrapper.BookWrapper " +
                "(b.name,b.authorName,b.status) from Book b where b.status='HAVE'")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "author_name")
    String authorName;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;
    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;


}
