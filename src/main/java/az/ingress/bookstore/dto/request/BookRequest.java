package az.ingress.bookstore.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String name;

    @Override
    public String toString() {
        return "BookRequest{name='%s'}".formatted(name);
    }
}
