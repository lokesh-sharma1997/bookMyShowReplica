package com.bookmyshow.main.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.NotificationDTO;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.model.Notification;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.NotificationRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.response.NotificationPageResponse;
import com.bookmyshow.main.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationPageResponse<NotificationDTO> getNotificationsForUser(Long userId, int page, int size) {
        UserMaster user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage =
                notificationRepository.findByUserOrderByCreatedOnDesc(user, pageable);

        // map entities → DTOs
        List<NotificationDTO> dtos = notificationPage
                .map(n -> modelMapper.map(n, NotificationDTO.class))
                .getContent();

        // get total count
        long totalCount = notificationRepository.countByUser_UserId(userId);

        return new NotificationPageResponse<>(totalCount, dtos);
    }



//    @Override
//    public NotificationDTO createNotification(Long userId, NotificationDTO dto) {
//        UserMaster user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
//
//        Notification notification = modelMapper.map(dto, Notification.class);
//        notification.setUser(user);
//        Notification saved = notificationRepository.save(notification);
//        return modelMapper.map(saved, NotificationDTO.class);
//    }

    @Override
    public String markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));

        // Check if the notification belongs to the given user
        if (!notification.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Notification does not belong to the specified user");
        }

        // Check if already read
        if (notification.isRead()) {
            return "Notification is already read by user";
        } else {
            notification.setRead(true);
            notificationRepository.updateReadStatus(notificationId, userId, true);

            return "Notification marked as read successfully";
        }
    }

    @Override
    public Long getUnreadCount(Long userId) {
        return notificationRepository.countByUser_UserIdAndReadFalse(userId);
    }
}
