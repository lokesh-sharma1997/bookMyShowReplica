package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.NotificationDTO;
import com.bookmyshow.main.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Get all notifications for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    // Create a new notification
    @PostMapping("/{userId}")
    public ResponseEntity<NotificationDTO> createNotification(@PathVariable Long userId,
                                                              @RequestBody NotificationDTO dto) {
        return ResponseEntity.ok(notificationService.createNotification(userId, dto));
    }

    // Mark notification as read
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }
}
