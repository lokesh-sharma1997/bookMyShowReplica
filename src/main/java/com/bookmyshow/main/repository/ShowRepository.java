package com.bookmyshow.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.Venue;

public interface ShowRepository extends JpaRepository<Show, Long> {

	Long save(ShowRequestDTO showDTO);
	// Before - this can throw error if no results
	

//	// After - safer way
//	Optional<Screen> findByVenueAndScreenName(Venue venue, String screenName);


}
