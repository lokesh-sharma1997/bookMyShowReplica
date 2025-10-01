package com.bookmyshow.main.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AvailableSlotRequest {
	 private Long venueId;
	    private LocalDate date;
	    private Long screenId;
}
