package vinhit.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectResponse {

    @JsonProperty("validToken")
    boolean validToken;

    // Constructor using @JsonCreator, if needed
    @JsonCreator
    public IntrospectResponse(@JsonProperty("validToken") boolean validToken) {
        this.validToken = validToken;
    }
}
