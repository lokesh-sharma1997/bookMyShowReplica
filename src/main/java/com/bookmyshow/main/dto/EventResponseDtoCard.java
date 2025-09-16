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
	private List<Integer> pricelist;
	private LocalTime starttime;
	private List<String> genres; 
	 private Double votes;    
	 private Double imdbRating;  
    private Boolean releasedFlag; 
    private List<String> languages;
    private List<String> categories;
}
