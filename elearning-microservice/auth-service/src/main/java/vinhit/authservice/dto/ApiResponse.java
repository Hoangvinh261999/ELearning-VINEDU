package vinhit.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse <T> {
   int code;
   String message;
   @Builder.Default
   long timestamp= Instant.now().getEpochSecond();
   @Builder.Default
   String privacy ="vinhit.com.vn";
   T result;

}

