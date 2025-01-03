package vinhit.authservice.service;

import vinhit.authservice.dto.request.CreateUserRequest;
import vinhit.authservice.dto.response.UserResponse;

import java.util.List;


public interface UserService  {
    String login(String username, String password);
    UserResponse userCreate(CreateUserRequest createUserRequest);
    UserResponse userTeacherCreate(CreateUserRequest createUserRequest);
    UserResponse userAdminCreate(CreateUserRequest createUserRequest);

    UserResponse getUser(Long usId);
    UserResponse getMyInformation(Long usId);
    List<UserResponse> getUsers();
    void  updateUser();
    void deleteUser();
}
