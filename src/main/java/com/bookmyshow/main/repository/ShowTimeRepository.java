
package com.bookmyshow.main.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bookmyshow.main.model.ShowTime;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
//
//    @Query("SELECT seat.id FROM ShowTime seat WHERE seat.showTimeDate.id = :showTimeDateId AND seat.isBooked = true")
//    List<Long> findBookedShowTimes(@Param("showTimeDateId") Long showTimeDateId);
}
