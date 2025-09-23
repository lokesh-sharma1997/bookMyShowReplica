package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.NotificationDTO;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    

    // Get all notifications for a user
    @GetMapping("/get-notification/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUserNotifications(@PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userId);

        ApiResponse<List<NotificationDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Notifications fetched successfully",
                true,
                notifications
        );

        return ResponseEntity.ok(response);
    }

	/*
	 * // Create a new notification
	 * 
	 * @PostMapping("/create-new-notification/{userId}") public
	 * ResponseEntity<ApiResponse<NotificationDTO>> createNotification(@PathVariable
	 * Long userId,
	 * 
	 * @RequestBody NotificationDTO dto) { NotificationDTO created =
	 * notificationService.createNotification(userId, dto);
	 * ApiResponse<NotificationDTO> response = new ApiResponse<>( 201,
	 * "Notification created successfully", true, created ); return
	 * ResponseEntity.status(201).body(response); }
	 */

    // Mark notification as read
    @PatchMapping("/{userId}/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable Long userId, @PathVariable Long notificationId) {
    	String message = notificationService.markAsRead(userId, notificationId);
        ApiResponse<Void> response = new ApiResponse<>(
                200,
                message,
                true,
                null
        );
        return ResponseEntity.ok(response);
    }
    
	/*
	 * // total count
	 * 
	 * @GetMapping("/get-notification-count/{userId}") public
	 * ResponseEntity<ApiResponse<Long>> getNotificationCount(@PathVariable Long
	 * userId) { Long count = notificationService.getNotificationCount(userId);
	 * return ResponseEntity.ok(new ApiResponse<>(200, "Total notifications count",
	 * true, count)); }
	 */

    // unread count
    @GetMapping("/get-notification-unread-count/{userId}")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(@PathVariable Long userId) {
        Long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Unread notifications count", true, count));
    }

}
