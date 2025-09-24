package com.bookmyshow.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowFetchDTO {

	private Long eventId;
	private String eventType;
	private String city;
	private String date;
}
