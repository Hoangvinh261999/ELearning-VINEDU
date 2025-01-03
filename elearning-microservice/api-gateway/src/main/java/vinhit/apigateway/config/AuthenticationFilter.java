package vinhit.apigateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vinhit.apigateway.dto.ApiResponse;
import vinhit.apigateway.service.AuthService;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {
    @Autowired
    AuthService authService;
    @Autowired
    ObjectMapper objectMapper;

    String [] public_endpoint= {
            "/auth-service/get/.*",
            "/auth-service/introspect",
            "/auth-service/login",
            "/auth-service/token",
            "/auth-service/users/registration",
            "/auth-service/users/registration/.*",
            "/notification-service/email/send",
            "/notification-service/email/send/.*"

    };
    @Value("${app.api-prefix}")
    private String apiPrefix;

    private boolean isPublicEndpoint(ServerHttpRequest request){
        return Arrays.stream(public_endpoint).anyMatch(s -> request.getURI().getPath().matches(apiPrefix+s));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(isPublicEndpoint(exchange.getRequest())){
            return chain.filter(exchange);
        }
        List<String> authHeader= exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if(CollectionUtils.isEmpty(authHeader)){
            return unauthenticated(exchange.getResponse());
        }
        String rawToken = authHeader.get(0);
        String token = rawToken.startsWith("Bearer ") ? rawToken.substring(7) : rawToken;
        return authService.validateToken(token).flatMap(introspectResponseApiResponse -> {
            if (introspectResponseApiResponse.getResult().isValidToken()){
                return chain.filter(exchange);

            }else {
                return unauthenticated(exchange.getResponse());
            }
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));

    }

    @Override
    public int getOrder() {
        return -1;
    }

    Mono<Void> unauthenticated (ServerHttpResponse response){
        ApiResponse<?> apiResponse=ApiResponse.builder()
                .code(1501)
                .message("Unauthenticated")
                .build();
        String body= null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );

    }
}
