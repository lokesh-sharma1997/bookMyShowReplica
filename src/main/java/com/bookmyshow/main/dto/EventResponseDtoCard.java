package com.bookmyshow.main.dto;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

@Data
public class EventResponseDtoCard {
	private Long eventId;       
	private String name;         
	private Double likes;        
	private String imageurl; 
	private LocalDate releasingOn;
	private LocalDate startDate;
	private List<String> venueName;
	private List<Integer> pricelist =List.of(400,500,800,1000,2000);
	private LocalTime starttime=LocalTime.of(14, 30);;
	private List<String> genres; 
	 private Double votes;    
	 private Double imdbRating;  
    private Boolean releasedFlag; 
    private int ageLimit;
    private List<String> languages;
    private List<String> categories;
}
