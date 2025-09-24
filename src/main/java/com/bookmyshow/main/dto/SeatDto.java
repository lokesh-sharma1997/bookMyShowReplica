package com.bookmyshow.main.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SeatDto {
    private Long seatId;
    private String row;             
    private Long number;            
    private String category;        
    
    private boolean reserved;       
    private List<String> reservedByUserId; 
}
