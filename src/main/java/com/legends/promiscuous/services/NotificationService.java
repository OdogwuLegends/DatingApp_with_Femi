package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.NotificationRequest;
import com.legends.promiscuous.dtos.response.NotificationResponse;

public interface NotificationService {
    NotificationResponse sendNotification(NotificationRequest notificationRequest);
}
