package az.ingress.bookstore.wrapper;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorWrapper {
    String name;
    String surname;
    Integer age;
}
