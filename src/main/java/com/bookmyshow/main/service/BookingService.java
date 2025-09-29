package com.bookmyshow.main.service;


import com.bookmyshow.main.dto.BookTicketRequestDTO;

public interface BookingService {
    void bookTickets(BookTicketRequestDTO dto);
}
