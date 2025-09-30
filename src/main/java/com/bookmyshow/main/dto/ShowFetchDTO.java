package com.bookmyshow.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowFetchDTO {
	 private String time;
	    private List<ShowCategoryDTO> availableCategories;
}
