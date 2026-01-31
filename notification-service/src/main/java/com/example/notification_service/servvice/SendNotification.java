package com.example.notification_service.servvice;

import com.example.notification_service.entity.Notifications;
import com.example.notification_service.repository.NotificationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendNotification {
    private final NotificationsRepository notificationsRepository;

    public void send(Long userId, String message) {
        Notifications notifications = new Notifications();
        notifications.setMessage(message);
        notifications.setUserId(userId);

        notificationsRepository.save(notifications);

    }
}
