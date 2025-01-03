package vinhit.apigateway.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vinhit.apigateway.dto.ApiResponse;
import vinhit.apigateway.dto.request.IntrospectRequest;
import vinhit.apigateway.dto.request.LoginRequest;
import vinhit.apigateway.dto.response.IntrospectResponse;
import vinhit.apigateway.dto.response.LoginResponse;
import vinhit.apigateway.repository.AuthServiceClient;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthService {
    AuthServiceClient authServiceClient;
    public Mono<ApiResponse<IntrospectResponse>> validateToken(String token){
        return authServiceClient.introspect(IntrospectRequest.builder().token(token).build()) ;
    }

    public Mono<ApiResponse<LoginResponse>> clientLogin(LoginRequest loginRequest){
        return authServiceClient.login(loginRequest) ;
    }
}
