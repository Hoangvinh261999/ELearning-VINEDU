package vinhit.apigateway.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    String username;
    String password;

}

