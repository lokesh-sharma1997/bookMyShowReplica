
package com.bookmyshow.main.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.BookTicketRequestDTO;
import com.bookmyshow.main.dto.BookingContentDTO;
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
import com.bookmyshow.main.repository.LayoutRepository;
import com.bookmyshow.main.repository.LayoutRowRepository;
import com.bookmyshow.main.repository.ScreenRepository;
import com.bookmyshow.main.repository.SeatRepository;
import com.bookmyshow.main.repository.ShowRepository;
import com.bookmyshow.main.repository.ShowTimeRepository;
import com.bookmyshow.main.repository.ShowtimedateRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.service.BookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ShowRepository showRepository;
	@Autowired
	private ScreenRepository screenRepository;
	@Autowired
	private VenueRepository venueRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ShowtimedateRepository showTimeDateRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private LayoutRepository layoutRepository;
	@Autowired
	private LayoutRowRepository layoutRowRepository;
	@Autowired
	private ShowTimeRepository showTimeRepository;

	@Override
	@Transactional
	public void bookTickets(BookTicketRequestDTO dto) {
		UserMaster user = validateUser(dto.getUserId());
		Event event = validateEvent(dto.getEventId());
		Venue venue = validateVenue(dto.getVenueId());
		Show show = validateShow(dto.getShowId(), event);

		boolean isMovieEvent = "Movie".equals(event.getEventType());

		if (isMovieEvent) {
			handleMovieBooking(dto, user, event, venue, show);
		} else {
			handleNonMovieBooking(dto, user, event, venue, show);
		}
	}

	private UserMaster validateUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	}

	private Event validateEvent(Long eventId) {
		return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
	}

	private Venue validateVenue(Long venueId) {
		return venueRepository.findById(venueId).orElseThrow(() -> new RuntimeException("Venue not found"));
	}

	private Show validateShow(Long showId, Event event) {
		Show show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));
		if (!show.getEvent().getEventId().equals(event.getEventId())) {
			throw new RuntimeException("Show does not belong to the given event");
		}
		return show;
	}

	private ShowTimeDate getShowTimeDate(Show show, LocalDate localDate) {
		return show.getShowstimedate().stream().filter(std -> std.getShowDate().equals(localDate)).findFirst()
				.orElseThrow(() -> new RuntimeException("No show available on the selected date"));
	}

	private ShowTime getShowTime(ShowTimeDate showTimeDate, LocalTime localTime) {
		return showTimeDate.getShowTimes().stream().filter(st -> st.getShowTime().equals(localTime)).findFirst()
				.orElseThrow(() -> new RuntimeException("No show at the selected time"));
	}

	private void handleMovieBooking(BookTicketRequestDTO dto, UserMaster user, Event event, Venue venue, Show show) {
		if (dto.getScreenId() == null) {
			throw new RuntimeException("ScreenId is required for movie events.");
		}

		Screen screen = screenRepository.findById(dto.getScreenId())
				.orElseThrow(() -> new RuntimeException("Screen not found"));

		if (!screen.getVenue().getId().equals(venue.getId())) {
			throw new RuntimeException("Screen does not belong to the given venue");
		}

		ShowTimeDate showTimeDate = getShowTimeDate(show, dto.getDate());
		ShowTime showTime = getShowTime(showTimeDate, dto.getTime());

		List<String> requestedSeats = dto.getReservedSeats();

		// Added validation for empty seat list
		if (requestedSeats == null || requestedSeats.isEmpty()) {
			throw new RuntimeException("At least one seat must be selected for booking");
		}

		List<String> normalizedRequestedSeats = requestedSeats.stream().map(String::trim).map(String::toUpperCase)
				.collect(Collectors.toList());

		// Validate seat format before database query
		validateSeatFormat(normalizedRequestedSeats);

		List<Seat> allSeatsInDatabase = seatRepository.findSeatsByNumberAndScreenIdWithoutStatusCheck(screen.getId(),
				normalizedRequestedSeats);

		// Strict validation - all requested seats must exist in database
		if (allSeatsInDatabase.size() != normalizedRequestedSeats.size()) {
			List<String> foundSeatNumbers = allSeatsInDatabase.stream().map(s -> s.getSeatNumber().trim().toUpperCase())
					.collect(Collectors.toList());
			List<String> invalidSeats = normalizedRequestedSeats.stream().filter(rs -> !foundSeatNumbers.contains(rs))
					.collect(Collectors.toList());
			throw new RuntimeException("INVALID_SEATS: These seats do not exist in the seat table: " + invalidSeats);
		}

		// Check for already booked seats
		List<String> alreadyReserved = bookingRepository
				.findBookedSeatsForShowTimeDate(showTimeDate.getId(), showTime.getId()).stream()
				.map(Seat::getSeatNumber).map(String::trim).map(String::toUpperCase).collect(Collectors.toList());

		List<String> conflict = normalizedRequestedSeats.stream().filter(alreadyReserved::contains)
				.collect(Collectors.toList());

		if (!conflict.isEmpty()) {
			throw new RuntimeException("ALREADY_BOOKED: Seats already booked for this show: " + conflict);
		}

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShow(show);
		booking.setVenue(venue);
		booking.setScreen(screen);
		booking.setEvent(event);
		booking.setShowTimeDate(showTimeDate);
		booking.setShowTime(showTime);
		booking.setBookingTime(LocalDateTime.now());
		booking.setSeats(allSeatsInDatabase);
		booking.setStatus("CONFIRMED");
		booking.setTotalPrice(dto.getTotalPrice());

		bookingRepository.save(booking);
		showTime.setIsBooked(true);
		showTimeRepository.save(showTime);
	}

	// New validation method to check seat name format
	private void validateSeatFormat(List<String> seatNumbers) {
		for (String seatNumber : seatNumbers) {
			// Seat format should be like: A1, B2, F10 (Letter + Number)
			if (!seatNumber.matches("^[A-Z]+\\d+$")) {
				throw new RuntimeException("INVALID_SEAT_FORMAT: Seat '" + seatNumber
						+ "' has invalid format. Expected format: Letter(s) followed by number(s) (e.g., A1, B2, F10)");
			}
		}
	}

	private void handleNonMovieBooking(BookTicketRequestDTO dto, UserMaster user, Event event, Venue venue, Show show) {
		Screen screen = null;
		if (dto.getScreenId() != null && dto.getScreenId() > 0) {
			screen = screenRepository.findById(dto.getScreenId())
					.orElseThrow(() -> new RuntimeException("Screen not found"));

			if (!screen.getVenue().getId().equals(venue.getId())) {
				throw new RuntimeException("Screen does not belong to the given venue");
			}
		}

		ShowTimeDate showTimeDate = getShowTimeDate(show, dto.getDate());
		ShowTime showTime = getShowTime(showTimeDate, dto.getTime());

		String eventSeats = null;
		if (dto.getEventSeats() != null && !dto.getEventSeats().isEmpty()) {
			eventSeats = String.join(",", dto.getEventSeats());
		}

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShow(show);
		booking.setEvent(event);
		booking.setVenue(venue);
		booking.setScreen(screen);
		booking.setShowTimeDate(showTimeDate);
		booking.setShowTime(showTime);
		booking.setBookingTime(LocalDateTime.now());
		booking.setEventSeats(eventSeats);
		booking.setStatus("CONFIRMED");
		booking.setTotalPrice(dto.getTotalPrice());

		bookingRepository.save(booking);
		showTime.setIsBooked(true);
		showTimeRepository.save(showTime);
	}

	@Override
	public List<String> getBookedSeats(Long showTimeDateId, Long showTimeId) {
		return bookingRepository.findBookedSeatsForShowTimeDate(showTimeDateId, showTimeId).stream()
				.map(Seat::getSeatNumber).toList();
	}

	@Override
	public List<BookingContentDTO> getAllBookingsByUser(Long userId) {
//	    List<Booking> bookings = bookingRepository.findAllByUserUserId(userId);
		List<Booking> bookings = bookingRepository.findByUser_UserId(userId);
		if (bookings.isEmpty()) {
			throw new RuntimeException("No bookings found for this user");
		}

		List<BookingContentDTO> bookingContents = new ArrayList<>();

		for (Booking booking : bookings) {
			int totalSeats = booking.getSeats() != null ? booking.getSeats().size() : 0;
			int totalAmount = totalSeats * booking.getShow().getShowPrice();

			BookingContentDTO content = new BookingContentDTO();
			content.setEventName(booking.getEvent().getName());
			content.setEventPoster(booking.getEvent().getImageurl());
			content.setVenue(booking.getVenue().getVenueName());
			content.setCity(booking.getVenue().getAddress().getCity().getName());

			// <CHANGE> Added null check for screen - it can be null for non-movie events
			content.setScreen(booking.getScreen() != null ? booking.getScreen().getScreenName() : "N/A");

			content.setDate(booking.getShowTimeDate().getShowDate().toString());
			content.setTime(booking.getShowTime().getShowTime().toString());

			// <CHANGE> Added null check for seats list
			content.setSeats(booking.getSeats() != null ? booking.getSeats().stream().map(Seat::getSeatNumber).toList()
					: new ArrayList<>());

			content.setTotalAmount(totalAmount);

			bookingContents.add(content);
		}

		return bookingContents;
	}
}
