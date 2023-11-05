package az.ingress.bookstore.dto.request;

import az.ingress.bookstore.consts.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String name;
    String authorName;
    Status status;

    @Override
    public String toString() {
        return "BookRequest{name='%s', authorName='%s', status=%s}"
                .formatted(name, authorName, status);
    }
}
