package vinhit.authservice.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vinhit.authservice.dto.ApiResponse;
import vinhit.authservice.dto.request.CreateDefaultUserUserProfileRequest;

@Service
@FeignClient(name = "profile-service", url = "http://localhost:8082")
public interface ProfileServiceWebClient {
    @PostMapping("/user-profile/default-create")
    ApiResponse<String> createDefaultProfile(@RequestBody CreateDefaultUserUserProfileRequest createUserRequest);
}
