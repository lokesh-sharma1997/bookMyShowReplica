package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class ShowTimeDTO {
	private LocalDate showDate;
	private List<LocalTime> showTime;
}