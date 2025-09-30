package com.bookmyshow.main.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.BookTicketRequestDTO;
import com.bookmyshow.main.model.Booking;
import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.ShowTime;
import com.bookmyshow.main.model.ShowTimeDate;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.BookingRepository;
import com.bookmyshow.main.repository.EventRepository;
import com.bookmyshow.main.repository.ScreenRepository;
import com.bookmyshow.main.repository.SeatRepository;
import com.bookmyshow.main.repository.ShowRepository;
import com.bookmyshow.main.repository.ShowTimeDateRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.service.BookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired private SeatRepository seatRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ShowRepository showRepository;
    @Autowired private ScreenRepository screenRepository;
    @Autowired private VenueRepository venueRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private ShowTimeDateRepository showTimeDateRepository;
    @Autowired private BookingRepository bookingRepository;

    
    @Override
    @Transactional
    public void bookTickets(BookTicketRequestDTO dto) 
    
    {
        UserMaster user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        Screen screen = screenRepository.findById(dto.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        if (!screen.getVenue().getId().equals(venue.getId())) {
            throw new RuntimeException("Screen does not belong to the given venue");
        }

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        if (!show.getEvent().getEventId().equals(event.getEventId())) {
            throw new RuntimeException("Show does not belong to the given event");
        }

        ShowTimeDate showTimeDate = show.getShowstimedate().stream()
                .filter(std -> std.getShowDate().equals(dto.getDate()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No show available on the selected date"));

        ShowTime showTime = showTimeDate.getShowTimes().stream()
                .filter(st -> st.getShowTime().equals(dto.getTime()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No show at the selected time"));

        List<Seat> allSeats = seatRepository.findByShowIdAndScreenId(show.getId(), screen.getId());

        List<String> alreadyReserved = allSeats.stream()
                .filter(Seat::isReserved)
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList());

        List<String> conflict = dto.getReservedSeats().stream()
                .filter(alreadyReserved::contains)
                .collect(Collectors.toList());

        if (!conflict.isEmpty()) {
            throw new RuntimeException("Seats already booked: " + conflict);
        }

        List<Seat> seatsToBook = allSeats.stream()
                .filter(seat -> dto.getReservedSeats().contains(seat.getSeatNumber()))
                .collect(Collectors.toList());

        for (Seat seat : seatsToBook) {
            seat.setReserved(true);
            seat.setUser(user);
        }

        seatRepository.saveAll(seatsToBook);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setVenue(venue);
        booking.setScreen(screen);
        booking.setEvent(event);
        booking.setShowTimeDate(showTimeDate);
        booking.setShowTime(showTime);
//        booking.setTotalSeats(seatsToBook.size());
        booking.setBookingTime(LocalDateTime.now());
        booking.setSeats(seatsToBook);

        bookingRepository.save(booking);
    }
    
    @Override
    public List<String> getBookedSeats(Long showId) {
        return bookingRepository.findByShowId(showId) 
                .stream()
                .flatMap(booking -> booking.getSeats().stream()) 
                .map(Seat::getSeatNumber) 
                .toList();
    }

}
