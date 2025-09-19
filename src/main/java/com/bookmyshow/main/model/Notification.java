package com.bookmyshow.main.model;

import java.time.LocalDateTime;

//import java.time.LocalDateTime;
//
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String type;        // MOVIE, SPORTS, THEATRE, STADIUM, ACTIVITY

    private boolean read = false;   // To mark notification as read/unread

    private LocalDateTime createdOn = LocalDateTime.now();

    // Optional: Link notification to User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserMaster user;

}
