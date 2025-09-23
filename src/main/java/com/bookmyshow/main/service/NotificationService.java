package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
//    NotificationDTO createNotification(Long userId, NotificationDTO dto);
    String markAsRead(Long userId, Long notificationId);
    List<NotificationDTO> getNotificationsForUser(Long userId);
    Long getUnreadCount(Long userId);
}
