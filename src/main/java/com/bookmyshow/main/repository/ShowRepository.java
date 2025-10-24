package com.bookmyshow.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmyshow.main.model.Show;

import io.lettuce.core.dynamic.annotation.Param;

public interface ShowRepository extends JpaRepository<Show, Long> {

	List<Show> findByVenueId(Long venueId);


	List<Show> findByVenueIdAndScreenId(Long venueId, Long screenId);

	 @Query("SELECT s FROM Show s JOIN s.showstimedate std " +
	           "WHERE s.event.eventId = :eventId AND std.showDate = :showDate")
	    List<Show> findByEventIdAndShowDate(@Param("eventId") Long eventId,
	                                        @Param("showDate") LocalDate showDate);

}