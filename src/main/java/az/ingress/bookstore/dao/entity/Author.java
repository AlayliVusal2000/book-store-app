package az.ingress.bookstore.dao.entity;

import az.ingress.bookstore.consts.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "authors")
public class Author {
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

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Book> books;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Subscriber> subscribers;

    @Override
    public String toString() {
        return "Author{id=%d, name='%s', surname='%s', age=%d, username='%s', password='%s', role=%s}"
                .formatted(id, name, surname, age, username, password, role);
    }
}


