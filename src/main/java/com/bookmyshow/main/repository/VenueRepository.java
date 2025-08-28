package com.bookmyshow.main.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Venue;

import java.util.List;
 

public interface VenueRepository extends JpaRepository<Venue, Long> {
	 List<Venue> findBynameIgnoreCase(String name);
}
