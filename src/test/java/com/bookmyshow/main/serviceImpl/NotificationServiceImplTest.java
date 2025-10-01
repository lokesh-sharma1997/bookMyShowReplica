package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.NotificationDTO;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.model.Notification;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.NotificationRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.response.NotificationPageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    private NotificationRepository notificationRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        notificationRepository = mock(NotificationRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        notificationService = new NotificationServiceImpl(notificationRepository, userRepository, modelMapper);
    }

    @Test
    void getNotificationsForUser_ShouldReturnNotifications() {
        Long userId = 1L;
        UserMaster user = new UserMaster();
        user.setUserId(userId);

        Notification notification = new Notification();
        notification.setNotificationId(100L);
        notification.setMessage("Test notification");
        notification.setCreatedOn(LocalDateTime.now());
        notification.setUser(user);

        Page<Notification> notificationPage = new PageImpl<>(Collections.singletonList(notification));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(notificationRepository.findByUserOrderByCreatedOnDesc(eq(user), any(Pageable.class)))
                .thenReturn(notificationPage);
        when(notificationRepository.countByUser_UserId(userId)).thenReturn(1L);

        NotificationPageResponse<NotificationDTO> response =
                notificationService.getNotificationsForUser(userId, 0, 10);

        assertEquals(1L, response.getCount());
        assertEquals(1, response.getContent().size());
        assertEquals("Test notification", response.getContent().get(0).getMessage());
    }

    @Test
    void getNotificationsForUser_ShouldThrow_WhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> notificationService.getNotificationsForUser(999L, 0, 10));
    }

    @Test
    void markAsRead_ShouldMarkSuccessfully() {
        Long userId = 1L;
        Long notificationId = 200L;

        UserMaster user = new UserMaster();
        user.setUserId(userId);

        Notification notification = new Notification();
        notification.setNotificationId(notificationId);
        notification.setUser(user);
        notification.setRead(false);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        String result = notificationService.markAsRead(userId, notificationId);

        assertEquals("Notification marked as read successfully", result);
        verify(notificationRepository).updateReadStatus(notificationId, userId, true);
    }

    @Test
    void markAsRead_ShouldThrow_WhenNotificationNotFound() {
        when(notificationRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> notificationService.markAsRead(1L, 999L));
    }

    @Test
    void markAsRead_ShouldThrow_WhenNotificationDoesNotBelongToUser() {
        Long userId = 1L;
        Long anotherUserId = 2L;
        Long notificationId = 300L;

        UserMaster otherUser = new UserMaster();
        otherUser.setUserId(anotherUserId);

        Notification notification = new Notification();
        notification.setNotificationId(notificationId);
        notification.setUser(otherUser);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        assertThrows(RuntimeException.class,
                () -> notificationService.markAsRead(userId, notificationId));
    }

    @Test
    void markAsRead_ShouldReturnAlreadyReadMessage() {
        Long userId = 1L;
        Long notificationId = 400L;

        UserMaster user = new UserMaster();
        user.setUserId(userId);

        Notification notification = new Notification();
        notification.setNotificationId(notificationId);
        notification.setUser(user);
        notification.setRead(true);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        String result = notificationService.markAsRead(userId, notificationId);

        assertEquals("Notification is already read by user", result);
        verify(notificationRepository, never()).updateReadStatus(any(), any(), anyBoolean());
    }

    @Test
    void getUnreadCount_ShouldReturnCorrectCount() {
        when(notificationRepository.countByUser_UserIdAndReadFalse(1L)).thenReturn(5L);

        Long count = notificationService.getUnreadCount(1L);

        assertEquals(5L, count);
    }
}
