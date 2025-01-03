package vinhit.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vinhit.authservice.dto.ApiResponse;
import vinhit.authservice.dto.request.CreateUserRequest;
import vinhit.authservice.dto.response.UserResponse;
import vinhit.authservice.service.UserService;

@CrossOrigin("*")
@Controller
@RestController
@RequestMapping("/auth-service/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/registration")
    public ApiResponse<UserResponse> UserRegister(@RequestBody CreateUserRequest createUserRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.userCreate(createUserRequest))
                .build();

    }
}
