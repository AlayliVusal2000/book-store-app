package az.ingress.bookstore.dao.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NamedQuery(name = "Subscriber.findStudentAndAuthorDetails", query = "SELECT  s.name,s.surname,s.age,s.username " +
        "FROM Subscriber sb " +
        "JOIN sb.student s " +
        "JOIN sb.author a where a.id=:authorId")
@NamedQuery(name = "Subscriber.findStudentAndAuthorDetail", query = "SELECT  a.name,a.surname,a.age,a.username " +
        "FROM Subscriber sb " +
        "JOIN sb.student s " +
        "JOIN sb.author a where s.id=:studentId")


@Getter
@Setter
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;
    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                '}';
    }
}
