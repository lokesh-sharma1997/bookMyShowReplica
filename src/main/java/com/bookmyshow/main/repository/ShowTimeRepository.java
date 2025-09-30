package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmyshow.main.model.ShowTimeDate;

public interface ShowTimeRepository extends JpaRepository<ShowTimeDate, Long>{
	@Query("SELECT s.id FROM ShowTime s WHERE s.showTimeDate.id = :showTimeDateId AND s.isBooked = true")
    List<Long> findBookedShowTimes(Long showTimeDateId);
}
