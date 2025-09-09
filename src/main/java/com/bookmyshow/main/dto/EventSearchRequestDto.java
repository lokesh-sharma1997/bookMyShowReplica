package com.bookmyshow.main.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class EventSearchRequestDto {
	
	  @Schema(example = "")
	 private String name;
	 
	  @Schema(example = "[]")
	    private List<String> eventTypes;
}

