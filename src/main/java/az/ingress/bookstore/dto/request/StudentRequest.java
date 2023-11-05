package az.ingress.bookstore.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    String name;
    String surname;
    Integer age;
    String username;

    @Override
    public String toString() {
        return "StudentRequest{name='%s', surname='%s', age=%d, username='%s'}"
                .formatted(name, surname, age, username);
    }
}
