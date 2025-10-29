package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bookmyshow.main.model.Booking;
import com.bookmyshow.main.model.Seat;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	// Find all bookings for a specific show
	List<Booking> findByShowId(Long showId);

	// <CHANGE> Fixed method name - use underscore for nested User.userId property
	List<Booking> findByUser_UserId(Long userId);

	// Find bookings by status
	List<Booking> findByStatus(String status);

	// Find bookings by show and status
	List<Booking> findByShowIdAndStatus(Long showId, String status);

	// Find bookings by show, screen and status
	List<Booking> findByShowIdAndScreenIdAndStatus(Long showId, Long screenId, String status);

	// Check if a specific seat is booked for a show
	@Query("SELECT CASE WHEN COUNT(booking) > 0 THEN true ELSE false END " +
	       "FROM Booking booking JOIN booking.seats seat " +
	       "WHERE booking.show.id = :showId AND seat.id = :seatId AND booking.status = 'CONFIRMED'")
	boolean isSeatBookedForShow(@Param("showId") Long showId, @Param("seatId") Long seatId);

	// Get all booked seats for a show
	@Query("SELECT DISTINCT seat FROM Booking booking JOIN booking.seats seat " +
	       "WHERE booking.show.id = :showId AND booking.status = 'CONFIRMED'")
	List<Seat> findBookedSeatsForShow(@Param("showId") Long showId);

	@Query("SELECT DISTINCT seat FROM Booking booking JOIN booking.seats seat " +
	       "WHERE booking.showTimeDate.id = :showTimeDateId AND booking.showTime.id = :showTimeId AND booking.status = 'CONFIRMED'")
	List<Seat> findBookedSeatsForShowTimeDate(@Param("showTimeDateId") Long showTimeDateId,
	                                          @Param("showTimeId") Long showTimeId);
}