package com.bookmyshow.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.Venue;

public interface ShowRepository extends JpaRepository<Show, Long> {

	Long save(ShowRequestDTO showDTO);


	List<Show> findByEventEventIdAndEventTypeAndCityAndDate(Long eventId, String eventType, String city, LocalDate showDate);
	

}
