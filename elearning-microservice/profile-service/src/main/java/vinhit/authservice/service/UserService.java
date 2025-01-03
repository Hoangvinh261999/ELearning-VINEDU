package vinhit.authservice.service;

import org.springframework.stereotype.Service;
import vinhit.authservice.dto.request.CreateDefaultUserProfileRequest;
import vinhit.authservice.dto.response.UserResponse;
import vinhit.authservice.entity.UserProfile;

import java.util.List;

@Service

public interface UserService  {
    boolean userCreate(CreateDefaultUserProfileRequest createUserProfileRequest);
    UserProfile findprofile(Long id, String where);
    UserResponse getUser(Long usId);
    UserResponse getMyInformation(Long usId);
    List<UserResponse> getUsers();
    void  updateUser();
    void deleteUser();
}
