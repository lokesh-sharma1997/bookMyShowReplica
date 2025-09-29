package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.BookTicketRequestDTO;
import com.bookmyshow.main.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> bookTickets(@RequestBody List<BookTicketRequestDTO> bookings) {
        try {
            for (BookTicketRequestDTO dto : bookings) {
                bookingService.bookTickets(dto);
            }
            return ResponseEntity.ok("Tickets booked successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking failed: " + e.getMessage());
        }
    }
}
