package com.bookmyshow.main.repository;

import com.bookmyshow.main.model.Notification;
import com.bookmyshow.main.model.UserMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Long countByUser_UserId(Long userId);

    Long countByUser_UserIdAndReadFalse(Long userId);
    
    // Pagination: get notifications for a user ordered by creation date
    Page<Notification> findByUserOrderByCreatedOnDesc(UserMaster user, Pageable pageable);

    // Optional: get unread notifications with pagination
    Page<Notification> findByUser_UserIdAndReadFalseOrderByCreatedOnDesc(Long userId, Pageable pageable);
}
