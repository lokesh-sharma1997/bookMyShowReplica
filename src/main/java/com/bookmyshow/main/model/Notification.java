package com.bookmyshow.main.model;

import java.time.LocalDateTime;

import com.bookmyshow.main.enumData.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    private String title;       // e.g. "New Movie Added"
    private String message;     // e.g. "Jawan is now available in theatres"
    
    @Enumerated(EnumType.STRING)
    private NotificationType type;		// EVENT, VENUE, BOOKING, etc.
    
    private boolean read = false;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Many notifications belong to 1 user
    private UserMaster user;
}
