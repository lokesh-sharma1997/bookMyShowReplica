package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getUserNotifications(Long userId);
    NotificationDTO createNotification(Long userId, NotificationDTO dto);
    void markAsRead(Long notificationId);
}
