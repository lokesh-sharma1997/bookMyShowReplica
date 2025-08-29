package com.bookmyshow.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDto {
    private String screenId;         
    private String name;             
    private List<SeatDto> layout;    
}
