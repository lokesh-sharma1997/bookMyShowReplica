package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.BookTicketRequestDTO;
import com.bookmyshow.main.dto.BookingContentDTO;
import com.bookmyshow.main.model.Booking;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/book")
	public ResponseEntity<ApiResponse<String>> bookTickets(@RequestBody List<BookTicketRequestDTO> bookings) {
		try {
			for (BookTicketRequestDTO dto : bookings) {
				bookingService.bookTickets(dto);
			}

			ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Tickets booked successfully!",
					true, "Tickets booked successfully!");

			return ResponseEntity.ok(response);

		} catch (RuntimeException e) {
			ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
					"Booking failed: " + e.getMessage(), false, null);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@GetMapping("/booked-seats")
	public ResponseEntity<ApiResponse<List<String>>> getBookedSeats(@RequestParam Long showId) {
		List<String> bookedSeats = bookingService.getBookedSeats(showId);

		ApiResponse<List<String>> response = new ApiResponse<>(HttpStatus.OK.value(),
				"Booked seats fetched successfully", true, bookedSeats);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/user/{userId}/shows")
	public ResponseEntity<ApiResponse<Map<String, List<BookingContentDTO>>>> getAllBookings(@PathVariable Long userId) {
	    try {
	        List<BookingContentDTO> bookingsContent = bookingService.getAllBookingsByUser(userId);

	        ApiResponse<Map<String, List<BookingContentDTO>>> response = new ApiResponse<>(
	                HttpStatus.OK.value(),
	                "Booking details fetched successfully",
	                true,
	                Map.of("content", bookingsContent)
	        );

	        return ResponseEntity.ok(response);

	    } catch (RuntimeException e) {
	        ApiResponse<Map<String, List<BookingContentDTO>>> response = new ApiResponse<>(
	                HttpStatus.BAD_REQUEST.value(),
	                "Failed to fetch bookings: " + e.getMessage(),
	                false,
	                null
	        );

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}



}