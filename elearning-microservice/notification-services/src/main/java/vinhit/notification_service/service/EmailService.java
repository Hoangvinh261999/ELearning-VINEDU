package vinhit.notification_service.service;

import vinhit.notification_service.brevo.request.ClientEmailRequest;
import vinhit.notification_service.brevo.request.EmailRequest;
import vinhit.notification_service.brevo.request.Sender;
import vinhit.notification_service.brevo.response.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vinhit.notification_service.httpclient.EmailClient;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    EmailClient emailClient;

    Dotenv dotenv = Dotenv.load();
    String apiKey = dotenv.get("SENDINBLUE_API_KEY");

    public EmailResponse sendEmail(ClientEmailRequest clientEmailRequest){
        EmailRequest emailRequest= EmailRequest.builder()
                .sender(Sender.builder()
                        .email("nguyenvinhdev260399@gmail.com")
                        .name("VinhEDU")
                        .build())
                .to(List.of(clientEmailRequest.getTo()))
                .subject(clientEmailRequest.getSubject())
                .htmlContent(clientEmailRequest.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey,emailRequest);

        }catch (Exception e){
            System.out.println(":Loi"+e.getMessage());
            throw e;
        }


    }
}
