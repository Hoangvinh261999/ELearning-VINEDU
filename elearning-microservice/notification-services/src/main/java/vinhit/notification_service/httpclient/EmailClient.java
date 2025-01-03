package vinhit.notification_service.httpclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vinhit.notification_service.brevo.request.EmailRequest;
import vinhit.notification_service.brevo.response.EmailResponse;


@FeignClient(name = "email-client",url = "https://api.brevo.com")
public interface EmailClient {
    @PostMapping(value = "/v3/smtp/email",produces = MediaType.APPLICATION_JSON_VALUE)
    EmailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequest emailRequest);
}
