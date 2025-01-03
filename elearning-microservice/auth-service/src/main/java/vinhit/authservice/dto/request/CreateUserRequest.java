package vinhit.authservice.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class CreateUserRequest {
    @Size(min = 6, message = "USERNAME_INVALID")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "USERNAME_INVALID_FORMAT") // Chỉ cho phép ký tự chữ cái, số, và dấu gạch dưới
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "INVALID_PASSWORD_FORMAT") // Password phải có ít nhất 1 chữ cái và 1 số
    String password;

}
