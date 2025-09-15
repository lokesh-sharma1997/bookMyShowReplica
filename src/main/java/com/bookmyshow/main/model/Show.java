package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="shows")
public class Show {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long eventid;
	private Long vanueid;
	private String city;
	private String eventType;
	private String date;
    private String startTime;
    private int duration;
    private String language;
    private String status;
//    private List<Category> categories;
    private String format;
    private String screenName;
    
	
}
