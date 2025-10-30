package com.bookmyshow.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShowLayoutDto {
	@JsonProperty("layout")
	private Long layoutId;

	private int moviePrice;

}