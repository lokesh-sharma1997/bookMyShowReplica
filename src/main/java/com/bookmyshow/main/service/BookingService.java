package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.dto.BookTicketRequestDTO;
import com.bookmyshow.main.dto.BookingContentDTO;
import com.bookmyshow.main.model.Booking;

public interface BookingService {
    void bookTickets(BookTicketRequestDTO dto);

	List<String> getBookedSeats(Long showId);
	
    List<BookingContentDTO> getAllBookingsByUser(Long userId);

}
