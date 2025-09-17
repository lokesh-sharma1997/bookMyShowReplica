package com.bookmyshow.main.dto;

import java.util.List;

import com.bookmyshow.main.model.Languages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private Long eventid;
    private Long venueid;
    private String city;
    private String eventType;
    private String date;
    private String startTime;
    private int duration;
    private List<String> languageName;  // <- updated to LanguagesDTO
    private String status;
    private String format;
    private String screen;
    private List<Integer> showprice;
    private List<SupportedCategoryDTO> supportedCategories;
    private List<ReserveSeatDTO> reserveSeat;
    
}
































//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class ShowDTO {
//
//    private Long id;
//    private Long eventid;  // Event ID
//    private Long venueid;  // Venue ID
//    private String city;
//    private String eventType;  // Movie, Event, etc.
//    private String date;
//    private String startTime;
//    private int duration;
//    private Languages language;
//    private String status;
//    private String format;  // IMAX, 2D, etc.
//    private String screen;
//    private List<Integer> showprice;
//    
//    
//    private List<SupportedCategoryDTO> supportedCategories;
//    
//    
//    
//
//}
