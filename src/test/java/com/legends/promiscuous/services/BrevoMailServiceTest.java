package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.EmailNotificationRequest;
import com.legends.promiscuous.dtos.requests.Recipient;
import com.legends.promiscuous.dtos.response.EmailNotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BrevoMailServiceTest {
    @Autowired
    private MailService mailService;
    private EmailNotificationRequest request;

    @BeforeEach
    public void setUp(){
        String recipientEmail = "rofime9859@royalka.com";
        String message = "<p>testing our mail service</p>";
        String subject = "test email";

        request = new EmailNotificationRequest();
        request.setMailContent(message);
        request.setRecipients(List.of(new Recipient(recipientEmail)));
        request.setSubject(subject);

    }
    @Test
    public void testThatEmailSendingWorks(){
        EmailNotificationResponse emailNotificationResponse = mailService.send(request);
        log.info("response:: {}", emailNotificationResponse);
        assertNotNull(emailNotificationResponse);
    }

}