package az.ingress.bookstore.dao.entity;

import az.ingress.bookstore.consts.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NamedQuery(name = "Student.getAllStudents",
        query = "select new az.ingress.bookstore.wrapper.StudentWrapper(s.name,s.surname,s.age,s.username) from Student s")

@NamedQuery(name = "Book.findBooksByStatus", query = "SELECT  b.name ,b.authorName,b.status " +
        "FROM Book b " +
        "JOIN b.student a where a.id=:studentId")

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "surname")
    String surname;
    @Column(name = "age")
    Integer age;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Book> books;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Subscriber> subscribers;

}


