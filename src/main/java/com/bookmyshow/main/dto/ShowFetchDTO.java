package com.bookmyshow.main.dto;

import java.util.List;
import lombok.Data;

@Data
public class ShowFetchDTO {
	private String time;
	private Long showTimeId;
	private Long showDateId;
	private List<ShowCategoryDTO> availableCategories;
}