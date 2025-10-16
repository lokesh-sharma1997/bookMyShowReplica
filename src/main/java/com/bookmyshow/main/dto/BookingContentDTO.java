package com.bookmyshow.main.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingContentDTO {
    private String eventName;
    private String eventPoster;
    private String venue;
    private String city;
    private String screen;
    private String date;
    private String time;
    private List<String> seats;
    private double totalAmount;
}
