package com.bookmyshow.main.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.Venue;

import java.util.List;
 

public interface VenueRepository extends JpaRepository<Venue, Long> {
	 List<Venue> findBynameIgnoreCase(String name);
	    List<Venue> findByCity(String city);
	    List<Venue> findByCityIgnoreCase(String city);
	  




}
