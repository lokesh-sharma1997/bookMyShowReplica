package com.bookmyshow.main.dto;

import lombok.Data;

@Data
public class CityDTO {
    private Long cityId;
    private String cityName;
    private Boolean popularCity;
    private String imageUrl;
}
