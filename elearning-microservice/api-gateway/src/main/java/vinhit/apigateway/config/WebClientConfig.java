package vinhit.apigateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import vinhit.apigateway.repository.AuthServiceClient;

@Configuration
public class WebClientConfig {
    @Bean
    WebClient authWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }
    @Bean
    AuthServiceClient authServiceClient( WebClient webClient){
        HttpServiceProxyFactory httpServiceProxyFactory= HttpServiceProxyFactory.builder(
                WebClientAdapter.forClient(webClient)
        ).build();
        return httpServiceProxyFactory.createClient(AuthServiceClient.class);
    }


}
