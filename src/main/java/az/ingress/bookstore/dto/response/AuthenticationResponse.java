package az.ingress.bookstore.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;

    @Override
    public String toString() {
        return "AuthenticationResponse{token='%s'}".formatted(token);
    }
}
