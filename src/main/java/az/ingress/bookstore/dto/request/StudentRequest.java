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
}
