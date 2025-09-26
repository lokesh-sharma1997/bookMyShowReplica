package com.bookmyshow.main.dto;

import java.time.LocalDateTime;

import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.model.Venue;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NotificationDTO {

    private Long notificationId;
	@Schema(defaultValue = "string")
    private String title;
    private String message;
    private String type;
    private boolean read;
    private LocalDateTime createdOn;

}
