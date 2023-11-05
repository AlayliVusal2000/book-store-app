package az.ingress.bookstore.security;

import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.dao.repo.AuthorRepository;
import az.ingress.bookstore.dao.repo.StudentRepository;
import az.ingress.bookstore.exception.UsernameNotFoundException;
import az.ingress.bookstore.exception.error.ErrorMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AuthorRepository authorRepository;
    private final StudentRepository studentRepository;

    @Getter
    private Author authorDetail;

    @Getter
    private Student studentDetail;

    public UserDetailServiceImpl(AuthorRepository authorRepository, StudentRepository studentRepository) {
        this.authorRepository = authorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", username);
        Author author = authorRepository.findByUsernameIgnoreCase(username);
        authorDetail = authorRepository.findByUsernameIgnoreCase(username);
        Student student = studentRepository.findByUsernameIgnoreCase(username);
        studentDetail = studentRepository.findByUsernameIgnoreCase(username);
        if (Objects.nonNull(author)) {
            return new User(author.getUsername(),
                    new BCryptPasswordEncoder().encode(author.getPassword()), new ArrayList<>());
        } else if (Objects.nonNull(student)) {
            return new User(student.getUsername(),
                    new BCryptPasswordEncoder().encode(student.getPassword()), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NAME_NOT_FOUND);
        }
    }

}