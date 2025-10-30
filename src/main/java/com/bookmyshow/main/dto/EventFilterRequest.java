package com.bookmyshow.main.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EventFilterRequest {
	@Schema(example = "")
	private String type;

	private Integer cityid;

	@Schema(example = "[]")
	private List<Integer> languages;

	@Schema(example = "[]")
	private List<Integer> genres;

	@Schema(example = "[]")
	private List<Integer> formats;

	@Schema(example = "[]")
	List<Integer> tags;

	@Schema(example = "[]")
	List<Integer> categories;

	@Schema(example = "[]")
	List<Integer> price;

	@Schema(example = "[]")
	List<Integer> morefilter;

	@Schema(example = "[]")
	List<Integer> releaseMonths;

	@Schema(example = "[]")
	List<Integer> dateFilters;

}
