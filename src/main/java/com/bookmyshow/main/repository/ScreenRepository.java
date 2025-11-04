package com.bookmyshow.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Venue;

import io.lettuce.core.dynamic.annotation.Param;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
//	
//	@Query("SELECT s FROM Screen s WHERE s.venue = :venue AND s.screenName = :screenName")
	Optional<Screen> findByVenueAndScreenName(Venue venue, String screenName);
//	
    List<Screen> findByVenueId(Long venueId);
	boolean existsByIdAndVenue_Id(Long screenId, Long venueId);

}
