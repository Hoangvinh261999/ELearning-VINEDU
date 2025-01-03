package vinhit.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NotificationEvent {
    String channel;
    String recipient;
    String templateCode;
    Map<String,Object> param;
    String subject;
    String body;
}
