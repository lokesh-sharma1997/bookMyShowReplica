package com.bookmyshow.main.events;

import com.bookmyshow.main.model.UserMaster;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationEvent extends ApplicationEvent {
    private final String title;
    private final String message;
    private final String type; // EVENT, VENUE, etc.

    public NotificationEvent(Object source, String title, String message, String type) {
        super(source);
        this.title = title;
        this.message = message;
        this.type = type;
    }
}
