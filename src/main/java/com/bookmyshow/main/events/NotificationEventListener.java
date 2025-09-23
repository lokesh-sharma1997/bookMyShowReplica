package com.bookmyshow.main.events;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.bookmyshow.main.enumData.NotificationType;
import com.bookmyshow.main.model.Notification;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.NotificationRepository;
import com.bookmyshow.main.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
            // Broadcast to all users
            List<UserMaster> users = userRepository.findAll();
            for (UserMaster user : users) {
                saveNotification(event, user);
            }
    }

    private void saveNotification(NotificationEvent event, UserMaster user) {
        Notification notification = new Notification();
        notification.setTitle(event.getTitle());
        notification.setMessage(event.getMessage());

        // Convert string type into enum
        try {
            notification.setType(NotificationType.valueOf(event.getType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            notification.setType(NotificationType.EVENT); // default fallback
        }

        notification.setUser(user);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

}

