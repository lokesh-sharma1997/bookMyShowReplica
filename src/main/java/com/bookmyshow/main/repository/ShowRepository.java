package com.bookmyshow.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

	List<Show> findByVenueId(Long venueId);

//	List<Show> findByVenueIdAndShowDate(Long venueId, Long screenId);

	Optional<Show> findByVenueIdAndScreenId(Long venueId, Long screenId);


}