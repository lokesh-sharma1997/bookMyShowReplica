package com.bookmyshow.main.dto;

import lombok.Data;

@Data
public class LayoutDTO {
    private Long id;
    private String layoutName;
    private String rows;
    private int cols;

}
