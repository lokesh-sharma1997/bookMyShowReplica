package com.bookmyshow.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventSearchDTO {
    private Long eventId;
    private String name;
}
