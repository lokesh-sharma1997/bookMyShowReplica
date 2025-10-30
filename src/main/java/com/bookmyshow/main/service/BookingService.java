
package com.bookmyshow.main.service;

import java.util.List;

import com.bookmyshow.main.dto.BookTicketRequestDTO;
import com.bookmyshow.main.dto.BookingContentDTO;

public interface BookingService {
	void bookTickets(BookTicketRequestDTO dto);

	List<String> getBookedSeats(Long showTimeDateId, Long showTimeId);

	List<BookingContentDTO> getAllBookingsByUser(Long userId);
}
