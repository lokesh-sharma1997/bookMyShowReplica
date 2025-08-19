package com.bookmyshow.main.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Movie {
  private Long id;
  private String title;
  private String language;
  private String geners;
  private String format;
  private String duration;
  private int rating;
  private int likes;
  
  @ManyToOne
  @JoinColumn(name = "city_id")
  private City city;
	
	
}
