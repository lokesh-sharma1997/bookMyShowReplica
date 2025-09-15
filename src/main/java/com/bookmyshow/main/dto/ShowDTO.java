package com.bookmyshow.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShowDTO {

    private Long id;
    private Long eventid;  // Event ID
    private Long venueid;  // Venue ID
    private String city;
    private String eventType;  // Movie, Event, etc.
    private String date;
    private String startTime;
    private int duration;
    private String language;
    private String status;
    private String format;  // IMAX, 2D, etc.
    private String screenName;
    
    // You can add a list of categories in the future if needed
    // private List<CategoryDTO> categories; 
}
