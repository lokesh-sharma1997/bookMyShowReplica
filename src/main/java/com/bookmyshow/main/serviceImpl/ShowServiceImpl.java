
package com.bookmyshow.main.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.ShowCategoryDTO;
import com.bookmyshow.main.dto.ShowFetchDTO;
import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.VenueShowDTO;
import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.BookingRepository;
import com.bookmyshow.main.repository.ShowRepository;
import com.bookmyshow.main.service.ShowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

	private final ShowRepository showRepository;
	private final BookingRepository bookingRepository;

	@Override
	public List<VenueShowDTO> getShows(ShowRequestDTO request) {
		LocalDate date = LocalDate.parse(request.getDate());

		List<Show> shows = showRepository.findByEventIdAndShowDate(request.getEventId(), date);

		return shows.stream().map(show -> {
			Venue venue = show.getVenue();
			Screen screen = show.getScreen();
			Event event = show.getEvent();

			List<ShowFetchDTO> showDtos = show.getShowstimedate().stream().filter(std -> std.getShowDate().equals(date))
					.flatMap(std -> std.getShowTimes().stream().map(st -> {
						ShowFetchDTO dto = new ShowFetchDTO();
						dto.setTime(st.getShowTime().toString());
						dto.setShowTimeId(st.getId());
						dto.setShowDateId(std.getId());

						List<ShowCategoryDTO> categories;

						if ("Movie".equalsIgnoreCase(event.getEventType()) && show.getShowLayouts() != null) {
							categories = show.getShowLayouts().stream().map(layout -> {
								String layoutName = layout.getLayout() != null ? layout.getLayout().getLayoutName()
										: null;
								String price = layout.getMoviePrice() != 0 ? String.valueOf(layout.getMoviePrice())
										: null;

								boolean anyReserved = layout.getLayout() != null
										&& layout.getLayout().getLayoutRows() != null
										&& layout.getLayout().getLayoutRows().stream()
												.flatMap(row -> row.getSeats().stream())
												.anyMatch(seat -> bookingRepository.isSeatBookedForShow(show.getId(),
														seat.getId()));

								String status = anyReserved ? "BOOKED" : "AVAILABLE";

								return new ShowCategoryDTO(layoutName, status, price);
							}).toList();
						} else {
							categories = List.of(new ShowCategoryDTO(null, "AVAILABLE",
									show.getShowPrice() != 0 ? String.valueOf(show.getShowPrice()) : null));
						}

						dto.setAvailableCategories(categories);
						return dto;
					})).toList();

			String screenId = null;
			if ("Movie".equalsIgnoreCase(event.getEventType()) && screen != null) {
				screenId = String.valueOf(screen.getId());
			}

			return new VenueShowDTO(venue.getVenueName(), String.valueOf(venue.getId()), screenId,
					String.valueOf(show.getId()), showDtos);
		}).toList();
	}

}
