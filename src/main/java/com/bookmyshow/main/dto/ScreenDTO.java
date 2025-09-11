package com.bookmyshow.main.dto;

import lombok.Data;
import java.util.List;

@Data
public class ScreenDTO {
    private Long id;
    private String screenName;
    private List<LayoutDTO> layouts; 

}
