package com.bookmyshow.main.repository;

import com.bookmyshow.main.model.Notification;
import com.bookmyshow.main.model.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedOnDesc(UserMaster user);
    Long countByUser_UserId(Long userId);

    Long countByUser_UserIdAndReadFalse(Long userId);
}
