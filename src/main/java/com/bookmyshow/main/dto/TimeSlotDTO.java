package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDTO {
//	@JsonIgnore
	
	private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
}
