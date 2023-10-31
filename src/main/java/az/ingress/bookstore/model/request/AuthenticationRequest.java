package az.ingress.bookstore.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    String username;
    String password;

    @Override
    public String toString() {
        return "AuthenticationRequest{username='%s'}".formatted(username);
    }
}
