package vinhit.notification_service.brevo.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponse {
    String messageId;
}
