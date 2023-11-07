package az.ingress.bookstore.wrapper;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentWrapper {

    String name;
    String surname;
    Integer age;
    String username;
}
