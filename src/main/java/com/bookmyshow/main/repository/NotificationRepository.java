package com.bookmyshow.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bookmyshow.main.model.Notification;
import com.bookmyshow.main.model.UserMaster;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Long countByUser_UserId(Long userId);

    Long countByUser_UserIdAndReadFalse(Long userId);
    
    // Pagination: get notifications for a user ordered by creation date
    Page<Notification> findByUserOrderByCreatedOnDesc(UserMaster user, Pageable pageable);

    // Optional: get unread notifications with pagination
    Page<Notification> findByUser_UserIdAndReadFalseOrderByCreatedOnDesc(Long userId, Pageable pageable);
    
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.read = :read WHERE n.notificationId = :notificationId AND n.user.userId = :userId")
    int updateReadStatus(@Param("notificationId") Long notificationId,
                         @Param("userId") Long userId,
                         @Param("read") boolean read);
}
