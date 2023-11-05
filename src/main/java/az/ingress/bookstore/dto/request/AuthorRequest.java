package az.ingress.bookstore.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorRequest {
    String name;
    String surname;
    Integer age;
    String username;

    @Override
    public String toString() {
        return "AuthorRequest{name='%s', surname='%s', age=%d, username='%s'}"
                .formatted(name, surname, age, username);
    }
}
