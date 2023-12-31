package az.ingress.bookstore.dto.response;

import az.ingress.bookstore.consts.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {

    String name;
    String surname;
    Integer age;
    String username;
    Role role;

    @Override
    public String toString() {
        return "RegisterResponse{name='%s', surname='%s', username='%s', role=%s}"
                .formatted(name, surname, username, role);
    }
}
