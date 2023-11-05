package az.ingress.bookstore.dto.response;

import az.ingress.bookstore.consts.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String name;
    String authorName;
    Status status;

    @Override
    public String toString() {
        return "BookResponse{name='%s', authorName='%s', status=%s}"
                .formatted(name, authorName, status);
    }
}
