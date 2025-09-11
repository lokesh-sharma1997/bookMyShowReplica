package com.bookmyshow.main.dto;



import java.util.List;
import lombok.Data;

@Data
public class EventResponseDtoCard {
	private Long eventId;       
	private String name;         
	private Double likes;        
	private String imageurl;        
	private List<String> genres; 
	 private Double votes;    
	 private Double imdbRating;  
    private Boolean releasedFlag; 
}
