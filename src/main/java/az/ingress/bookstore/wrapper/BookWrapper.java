package az.ingress.bookstore.wrapper;

import az.ingress.bookstore.consts.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookWrapper {
    String name;
    String authorName;
    Status status;
}
