package vinhit.authservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vinhit.authservice.dto.ApiResponse;
import vinhit.authservice.dto.request.CreateDefaultUserProfileRequest;
import vinhit.authservice.entity.UserProfile;
import vinhit.authservice.service.UserService;

@CrossOrigin("*")
@Controller
@RestController
@Slf4j
@RequestMapping("/user-profile")
public class UserProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}/{where}")
    public ApiResponse<UserProfile> profileUser(@PathVariable Long id,@PathVariable String where) {
          return ApiResponse.<UserProfile>builder()
                  .result(userService.findprofile(id,where))
                  .build();
    }

    @PostMapping("/default-create")
    public ApiResponse<String> createUserProfile(@RequestBody CreateDefaultUserProfileRequest createUserProfileRequest){
        return ApiResponse.<String>builder()
                .code(1000)
                .result(String.valueOf(userService.userCreate(createUserProfileRequest)))
                .build();
    }


    @PostMapping("/update")
    public ApiResponse<?>updateUserProfile(@RequestBody CreateDefaultUserProfileRequest createUserProfileRequest){

        return null;
    }

}
