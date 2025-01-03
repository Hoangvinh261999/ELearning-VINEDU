package vinhit.authservice.controller;

import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vinhit.authservice.dto.ApiResponse;
import vinhit.authservice.dto.request.AuthenticationRequest;
import vinhit.authservice.dto.request.IntrospectRequest;
import vinhit.authservice.dto.response.IntrospectResponse;
import vinhit.authservice.service.JwtService;
import vinhit.authservice.service.ServiceImplement.UserServiceImp;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@Controller
@RequestMapping("/auth-service")
@Tag(name = "UserController with auth service", description = "Auth service")
public class AuthenticationController {
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    JwtService jwtService;
    @Operation(summary = "Login by user", description = "Login from user to website")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "User name or password invalid"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully login"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Not valid author. Try access system by correct authorities"),

    })
    @GetMapping("/token")
   ApiResponse<?> authenticate(@RequestBody AuthenticationRequest request){
        return

                        ApiResponse.<String>builder()
                                .message("done")
                                .result(userServiceImp.login(request.getUsername(),request.getPassword()))
                                .build();
    }
    @PostMapping("/login")

    ApiResponse<?> login(@RequestBody AuthenticationRequest request){
        return

                ApiResponse.<String>builder()
                        .message("Login successful")
                        .result(userServiceImp.login(request.getUsername(),request.getPassword()))
                        .build()
                ;

    }
    @Operation(summary = "Check valid token",description = "Return true if valid jwt token")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1000",description = "valid token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1001",description = "invalid token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1002",description = "experied token")})
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspectToken(@RequestBody IntrospectRequest introspectRequest){
        var result = jwtService.validateToken(introspectRequest.getToken());
        IntrospectResponse introspectResponse= new IntrospectResponse(result);
        if(result){
            return ApiResponse.<IntrospectResponse>builder()
                    .code(1000)
                    .message("Token is valid")
                    .result(introspectResponse)
                    .build();
        }else {
            return ApiResponse.<IntrospectResponse>builder()
                    .code(1500)
                    .message("Token is not valid")
                    .result(introspectResponse)
                    .build();
        }
        }




    @PostMapping("/refesh")
    public ApiResponse<?> refeshToken(@RequestBody String token){
      try {
          return ApiResponse.builder()
                  .code(1010)
                  .result(jwtService.refeshJwtToken(token)).build();
      } catch (ParseException | JOSEException e) {
          return ApiResponse.builder()
                  .code(1011)
                  .message("Invalid token")
                  .result("Invalid").build();
      }
    }


    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestBody IntrospectRequest token) {
           var logout=jwtService.logout(token.getToken());
            return ApiResponse.builder()
                    .message("Logged out")
                    .code(1111)
                    .result(logout)
                    .build();
    }

}
