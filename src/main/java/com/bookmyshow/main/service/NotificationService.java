package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.NotificationDTO;
import com.bookmyshow.main.response.NotificationPageResponse;

public interface NotificationService {
//    NotificationDTO createNotification(Long userId, NotificationDTO dto);
    String markAsRead(Long userId, Long notificationId);
    NotificationPageResponse<NotificationDTO> getNotificationsForUser(Long userId, int page, int size);
    Long getUnreadCount(Long userId);
}
