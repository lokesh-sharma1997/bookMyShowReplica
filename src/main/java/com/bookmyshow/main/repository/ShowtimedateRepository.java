package com.bookmyshow.main.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmyshow.main.model.ShowTime;
import com.bookmyshow.main.model.ShowTimeDate;

import io.lettuce.core.dynamic.annotation.Param;

public interface ShowtimedateRepository extends JpaRepository<ShowTimeDate, Long>{

    @Query("SELECT st FROM ShowTime st WHERE st.showTimeDate.id = :showTimeDateId AND st.isBooked = true")
    List<ShowTime> findBookedShowTimes(@Param("showTimeDateId") Long showTimeDateId);

    List<ShowTimeDate> findByShow_Venue_IdAndShow_Screen_IdAndShowDate(
            Long venueId,
            Long screenId,
            LocalDate showDate
    );    

}
	