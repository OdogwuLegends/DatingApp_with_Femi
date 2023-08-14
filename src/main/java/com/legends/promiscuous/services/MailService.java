package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.EmailNotificationRequest;
import com.legends.promiscuous.dtos.response.EmailNotificationResponse;

public interface MailService {
    EmailNotificationResponse send(EmailNotificationRequest emailNotificationRequest);
}
