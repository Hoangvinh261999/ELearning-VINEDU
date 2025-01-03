package vinhit.authservice.service.ServiceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vinhit.authservice.dto.request.CreateDefaultUserUserProfileRequest;
import vinhit.authservice.dto.request.CreateUserRequest;
import vinhit.authservice.dto.response.UserResponse;
import vinhit.authservice.entity.Permission;
import vinhit.authservice.entity.User;
import vinhit.authservice.enums.Role;
import vinhit.authservice.exception.AppException;
import vinhit.authservice.exception.ErrorCode;
import vinhit.authservice.mapper.UserMapper;
import vinhit.authservice.repository.RoleRepository;
import vinhit.authservice.repository.UserRepository;
import vinhit.authservice.repository.httpclient.ProfileServiceWebClient;
import vinhit.authservice.service.JwtService;
import vinhit.authservice.service.UserService;
import vinhit.event.dto.NotificationEvent;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailServiceImp userDetailServiceImp;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    ProfileServiceWebClient profileServiceWebClient;
    public String login(String username, String password) {
        UserDetails userDetails=userDetailServiceImp.loadUserByUsername(username);
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails,password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.generateToken(userRepository.findByUsername(username));
    }

    @Override
    public UserResponse userCreate(CreateUserRequest createUserRequest) {
        if(userRepository.findByUsername(createUserRequest.getUsername())!=null)
                    throw new AppException(ErrorCode.USER_EXISTED);
                User user=userMapper.toUser(createUserRequest);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                Permission permission =  new Permission();
                permission.setRole_id(roleRepository.findByRoleName(Role.User));
                List<Permission> roles= new ArrayList<>();
                roles.add(permission);
                user.setRoles(roles);
                userRepository.save(user);
        profileServiceWebClient.createDefaultProfile( CreateDefaultUserUserProfileRequest.builder()
                .userid(userRepository.findByUsername(user.getUsername()).getId())
                .build());

        NotificationEvent notificationEvent=NotificationEvent.builder()
                .channel("Email")
                .recipient(createUserRequest.getUsername())
                .subject("Welcome to VINEDU")
                .body("Hello, "+createUserRequest.getUsername())
                .build();
            //public message to kafka
        kafkaTemplate.send("notification-delivery",notificationEvent);
        return userMapper.toUserCreateResponse(user);
    }

    @Override
    public UserResponse userTeacherCreate(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public UserResponse userAdminCreate(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public UserResponse getUser(Long usId) {
        return null;
    }

    @Override
    public UserResponse getMyInformation(Long usId) {
        return null;
    }

    @Override
    public List<UserResponse> getUsers() {
        return null;
    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }
}
