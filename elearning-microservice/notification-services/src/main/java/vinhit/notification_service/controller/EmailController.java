package vinhit.notification_service.controller;


import vinhit.notification_service.brevo.ApiResponse;
import vinhit.notification_service.brevo.request.ClientEmailRequest;
import vinhit.notification_service.brevo.response.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vinhit.notification_service.service.EmailService;
@RestController
@CrossOrigin("*")
@RequestMapping("/service_email")
public class EmailController {
    @Autowired
    EmailService emailService;
    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody ClientEmailRequest clientEmailRequest){
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(clientEmailRequest))
                .build();
    }

}
