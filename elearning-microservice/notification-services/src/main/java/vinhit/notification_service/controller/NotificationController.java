package vinhit.notification_service.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vinhit.event.dto.NotificationEvent;
import vinhit.notification_service.brevo.request.ClientEmailRequest;
import vinhit.notification_service.brevo.request.Recipient;
import vinhit.notification_service.service.EmailService;

@Component
@Slf4j
public class NotificationController {
    @Autowired
    EmailService emailService;
    @KafkaListener(topics = "notification-delivery")
    public void listen(NotificationEvent message){
        String emailContent = generateWelcomeEmailContent(message);
        emailService.sendEmail(ClientEmailRequest.builder()
                .to(Recipient.builder()
                        .email(message.getRecipient())
                        .name("kuteboylove1999@gmail.com") // Tên của người nhận
                        .build())
                .subject(message.getSubject())
                .htmlContent(emailContent)
                .build());
    }

    private String generateWelcomeEmailContent(NotificationEvent message) {
        // Tạo nội dung email bằng HTML với thiết kế đẹp và chuyên nghiệp
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f9f9f9; }" +
                ".email-container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }" +
                ".email-header { background-color: #4CAF50; color: white; text-align: center; padding: 20px; border-radius: 8px 8px 0 0; }" +
                ".email-body { margin: 20px 0; font-size: 16px; color: #333; line-height: 1.6; }" +
                ".email-footer { font-size: 14px; text-align: center; color: #777; margin-top: 20px; border-top: 1px solid #eee; padding-top: 10px; }" +
                ".btn { display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='email-header'>" +
                "<h2>Welcome to Our Service!</h2>" +
                "</div>" +
                "<div class='email-body'>" +
                "<p>Hi " + message.getRecipient() + ",</p>" +
                "<p>Thank you for joining us! We're excited to have you on board. Here are the details of your recent notification:</p>" +
                "<p><strong>Username:</strong> " + message.getRecipient() + "</p>" +
                "<p><strong>Message:</strong> " + "You need to update profile to learn !" + "</p>" +
                "<a href='#' class='btn'>Update profile</a>" +
                "</div>" +
                "<div class='email-footer'>" +
                "<p>Best regards, <br/>Your Team</p>" +
                "<p>If you have any questions, feel free to <a href='#' style='color: #4CAF50;'>contact us</a>.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

}
