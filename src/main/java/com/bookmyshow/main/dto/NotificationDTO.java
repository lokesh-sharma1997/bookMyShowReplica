package com.bookmyshow.main.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private String title;
    private String message;
    private String type;
    private boolean read;
    private LocalDateTime createdOn;

}
