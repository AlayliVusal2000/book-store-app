package az.ingress.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponse {
    String name;
    String surname;
    Integer age;
}
