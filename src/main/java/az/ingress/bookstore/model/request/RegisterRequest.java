package az.ingress.bookstore.model.request;

import az.ingress.bookstore.consts.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    String name;
    String surname;
    Integer age;
    String username;
    String password;

    @Override
    public String toString() {
        return "RegisterRequest{name='%s', surname='%s', username='%s', password='%s'}"
                .formatted(name, surname, username, password);
    }
}
