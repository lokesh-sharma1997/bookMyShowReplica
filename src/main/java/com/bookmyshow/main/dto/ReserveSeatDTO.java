package com.bookmyshow.main.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReserveSeatDTO {
 private Long Userid;
 
 private List<String> userReservationSeats;
}
