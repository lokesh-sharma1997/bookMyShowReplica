//package com.bookmyshow.main.repository;
//	
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.bookmyshow.main.model.Booking;
//import com.bookmyshow.main.model.UserMaster;
//
//import io.lettuce.core.dynamic.annotation.Param;
//
//public interface BookingRepository extends JpaRepository<Booking,Long> {
//
//    List<Booking> findByShowId(Long showId);
//    List<Booking>findAllByUserUserId(Long userid);
//
//  
//}
//	
package com.bookmyshow.main.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bookmyshow.main.model.Booking;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.UserMaster;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	
	// Find all bookings for a specific show
	List<Booking> findByShowId(Long showId);
	
	// Find all bookings by a user
	List<Booking> findAllByUserUserId(Long userId);
	
//	// Find bookings by show and user
//	Optional<Booking> findByShowIdAndUserId(Long showId, Long userId);
	
	// Find bookings by status
	List<Booking> findByStatus(String status);
	
	// Find bookings by show and status
	List<Booking> findByShowIdAndStatus(Long showId, String status);
	
	// Find bookings by show, screen and status
	List<Booking> findByShowIdAndScreenIdAndStatus(Long showId, Long screenId, String status);
	
	
	// Check if a specific seat is booked for a show
	@Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
	       "FROM Booking b JOIN b.seats s " +
	       "WHERE b.show.id = :showId AND s.id = :seatId AND b.status = 'CONFIRMED'")
	boolean isSeatBookedForShow(@Param("showId") Long showId, @Param("seatId") Long seatId);
	
	// Get all booked seats for a show
	@Query("SELECT DISTINCT s FROM Booking b JOIN b.seats s " +
	       "WHERE b.show.id = :showId AND b.status = 'CONFIRMED'")
	List<Seat> findBookedSeatsForShow(@Param("showId") Long showId);
	
	@Query("SELECT DISTINCT s FROM Booking b JOIN b.seats s " +
		       "WHERE b.showTimeDate.id = :showTimeDateId AND b.showTime.id = :showTimeId AND b.status = 'CONFIRMED'")
		List<Seat> findBookedSeatsForShowTimeDate(@Param("showTimeDateId") Long showTimeDateId, @Param("showTimeId") Long showTimeId);
	}


 



