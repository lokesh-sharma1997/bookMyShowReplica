package com.bookmyshow.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
    private Long seatId;
    private String row;        
    private Long number;       
    private String category;   
}
