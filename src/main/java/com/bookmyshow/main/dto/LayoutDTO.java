package com.bookmyshow.main.dto;

import java.util.List;

import com.bookmyshow.main.model.LayoutRow;
import com.bookmyshow.main.model.Screen;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LayoutDTO {

	private Long id;
	private String layoutName;
	private List<String> rows;
	private int cols;

	@JsonIgnore
	private int price;

	@JsonIgnore
	private Long ScreenId;

}
