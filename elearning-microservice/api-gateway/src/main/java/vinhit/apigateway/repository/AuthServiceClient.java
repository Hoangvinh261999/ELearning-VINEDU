package vinhit.apigateway.repository;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import vinhit.apigateway.dto.ApiResponse;
import vinhit.apigateway.dto.request.IntrospectRequest;
import vinhit.apigateway.dto.request.LoginRequest;
import vinhit.apigateway.dto.response.IntrospectResponse;
import vinhit.apigateway.dto.response.LoginResponse;

@Service
public interface AuthServiceClient {
    @PostExchange(url = "/auth-service/introspect",contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest introspectRequest);

    @PostExchange(url = "/auth-service/login",contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest);
}
