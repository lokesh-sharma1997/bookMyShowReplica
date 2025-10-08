package com.bookmyshow.main.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.ShowCategoryDTO;
import com.bookmyshow.main.dto.ShowFetchDTO;
import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.VenueShowDTO;
import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.model.Layout;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.ShowRepository;
import com.bookmyshow.main.service.ShowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

	private final ShowRepository showRepository;

	@Override
	public List<VenueShowDTO> getShows(ShowRequestDTO request) {
		LocalDate date = LocalDate.parse(request.getDate());

		List<Show> shows = showRepository.findByEventIdAndShowDate(request.getEventId(), date);

		return shows.stream().map(show -> {
			Venue venue = show.getVenue();
			Screen screen = show.getScreen();
			Layout layout = show.getLayout();
			Event event = show.getEvent();

			List<ShowFetchDTO> showDtos = show.getShowstimedate().stream().filter(std -> std.getShowDate().equals(date))
					.flatMap(std -> std.getShowTimes().stream()).map(st -> {
						ShowFetchDTO dto = new ShowFetchDTO();
						dto.setTime(st.getShowTime().toString());

						List<ShowCategoryDTO> categories = List.of();

						if ("MOVIE".equalsIgnoreCase(event.getEventType()) && layout != null) {
							categories = layout.getLayoutRows().stream().map(row -> {
								boolean anyReserved = row.getSeats().stream().anyMatch(Seat::isReserved);
								String status = anyReserved ? "BOOKED" : "AVAILABLE";

								return new ShowCategoryDTO(layout.getLayoutName(), status,
										String.valueOf(show.getShowPrice()));
							}).toList();
						} else {
							categories = List
									.of(new ShowCategoryDTO(null, "AVAILABLE", String.valueOf(show.getShowPrice())));
						}

						dto.setAvailableCategories(categories);
						return dto;
					}).toList();

			String screenId = null;
			if ("MOVIE".equalsIgnoreCase(event.getEventType()) && screen != null) {
				screenId = String.valueOf(screen.getId());
			}

			return new VenueShowDTO(venue.getVenueName(), String.valueOf(venue.getId()), screenId,
					String.valueOf(show.getId()), showDtos);
		}).toList();
	}
}
