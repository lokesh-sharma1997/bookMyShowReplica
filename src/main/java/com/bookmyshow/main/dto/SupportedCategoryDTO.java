package com.bookmyshow.main.dto;

import java.util.List;

import lombok.Data;

@Data
public class SupportedCategoryDTO {
	private Long id;
    private String categoryName;
//    private Integer price;
    private String Layoutname;
    private List<ReserveSeatDTO> reservedSeats;
}
