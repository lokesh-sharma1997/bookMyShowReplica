package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.dto.BookTicketRequestDTO;

public interface BookingService {
    void bookTickets(BookTicketRequestDTO dto);

	List<String> getBookedSeats(Long showId);
}
