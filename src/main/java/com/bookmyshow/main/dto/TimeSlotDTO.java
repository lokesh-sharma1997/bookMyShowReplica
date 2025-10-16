package com.bookmyshow.main.dto;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
