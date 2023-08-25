package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.NotificationRequest;
import com.legends.promiscuous.dtos.response.NotificationResponse;
import com.legends.promiscuous.models.Notification;
import com.legends.promiscuous.models.User;
import com.legends.promiscuous.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.legends.promiscuous.dtos.response.ResponseMessage.NOTIFICATION_SENT_SUCCESSFULLY;

@Service
@AllArgsConstructor
public class PromiscuousNotification implements NotificationService {
    @Autowired
    private final UserService userService;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        Long sender_id = notificationRequest.getSender_id();
        Long receivers_id = notificationRequest.getRecipients_id();
        String header = notificationRequest.getSubject();

        Notification notification = new Notification();
        notification.setSenderId(sender_id);

        User foundUser = userService.findUserById(receivers_id);
        notification.setUser(foundUser);

        notification.setContent(header);

        notificationRepository.save(notification);

        return NotificationResponse.builder()
                .message(NOTIFICATION_SENT_SUCCESSFULLY.name())
                .build();

    }

}










