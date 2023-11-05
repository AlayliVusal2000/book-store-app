package az.ingress.bookstore.dto.response;

import az.ingress.bookstore.consts.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {
    String name;
    String surname;
    Integer age;
    String username;
    Role role;

    @Override
    public String toString() {
        return "StudentResponse{name='%s', surname='%s', age=%d, username='%s', role=%s}"
                .formatted(name, surname, age, username, role);
    }
}
