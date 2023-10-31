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
    String username;
    String email;
    String password;
    Role role;

    @Override
    public String toString() {
        return "RegisterRequest{name='%s', surname='%s', username='%s', email='%s', password='%s', role=%s}"
                .formatted(name, surname, username, email, password, role);
    }
}
