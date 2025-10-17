package com.bookmyshow.main.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.AddressDTO;
import com.bookmyshow.main.dto.LayoutDTO;
import com.bookmyshow.main.dto.LayoutRowDTO;
import com.bookmyshow.main.dto.ScreenDTO;
import com.bookmyshow.main.dto.TimeSlotDTO;
import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.exception.VenueNotFoundException;
import com.bookmyshow.main.model.Address;
import com.bookmyshow.main.model.Amenity;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.model.Layout;
import com.bookmyshow.main.model.LayoutRow;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.ShowTime;
import com.bookmyshow.main.model.ShowTimeDate;
import com.bookmyshow.main.model.SupportedCategory;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.*;
import com.bookmyshow.main.service.VenueService;

import jakarta.transaction.Transactional;

import com.bookmyshow.main.events.NotificationEvent;

@Service
public class VenueServiceImpl implements VenueService {

	private final SeatRepository seatRepository;

	@Autowired
	private VenueRepository venueRepository;

	@Autowired
	private AmenityRepository amenityRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private ShowtimedateRepository showtimedateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private LayoutRepository layoutRepository;

	@Autowired
	private ScreenRepository screenRepository;

	@Autowired
	private LayoutRowRepository layoutRowRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ShowTimeRepository showTimeRepository;

	VenueServiceImpl(SeatRepository seatRepository) {
		this.seatRepository = seatRepository;
	}

	// Convert entity to DTO
	private VenueDTO entityToDto(Venue entity) {
		if (entity == null) {
			return null;
		}

		VenueDTO dto = new VenueDTO();
		dto.setId(entity.getId());
		dto.setVenueName(entity.getVenueName());
		dto.setVenueCapacity(entity.getVenueCapacity());
//        dto.setVenueFor(entity.getVenueFor());
		dto.setVenueType(entity.getVenueType());

		if (entity.getAddress() != null) {
			AddressDTO addressDto = new AddressDTO();
			addressDto.setStreet(entity.getAddress().getStreet());
			addressDto.setPin(entity.getAddress().getPin());

			if (entity.getAddress().getCity() != null) {
				addressDto.setCityName(entity.getAddress().getCity().getName());
			}

			dto.setAddress(addressDto);
		}
		if (entity.getAmenities() != null) {
			List<String> amenityNames = entity.getAmenities().stream().map(Amenity::getAmenityName)
					.collect(Collectors.toList());
			dto.setAmenities(amenityNames);
		}

		if (entity.getSupportedCategories() != null) {
			List<String> supportedCategoryNames = entity.getSupportedCategories().stream()
					.map(SupportedCategory::getCategoryname).collect(Collectors.toList());
			dto.setSupportedCategories(supportedCategoryNames);
		}

		if ("movie".equalsIgnoreCase(entity.getVenueType()) && entity.getScreens() != null) {
			List<ScreenDTO> screenDTOs = entity.getScreens().stream().map(screen -> {
				ScreenDTO screenDto = new ScreenDTO();
				screenDto.setId(screen.getId());
				screenDto.setScreenName(screen.getScreenName());

				List<LayoutDTO> layoutDTOs = screen.getLayouts().stream().map(layout -> {
					LayoutDTO layoutDto = new LayoutDTO();
					layoutDto.setId(layout.getId());
					layoutDto.setLayoutName(layout.getLayoutName());
					layoutDto.setCols(layout.getCols());
					layoutDto.setScreenId(screen.getId());

					if (layout.getLayoutRows() != null) {
						List<String> rowStrings = layout.getLayoutRows().stream().map(LayoutRow::getRowName)
								.collect(Collectors.toList());
						layoutDto.setRows(rowStrings);
					}

					return layoutDto;
				}).collect(Collectors.toList());

				screenDto.setLayouts(layoutDTOs);
				return screenDto;
			}).collect(Collectors.toList());

			dto.setScreens(screenDTOs);
		}

		return dto;
	}

	private Venue dtoToEntity(VenueDTO dto) {
		if (dto == null) {
			return null;
		}

		Venue entity = new Venue();
		entity.setVenueName(dto.getVenueName());
		entity.setVenueCapacity(dto.getVenueCapacity());
//        entity.setVenueFor(dto.getVenueFor());
		entity.setVenueType(dto.getVenueType());

		if (dto.getAmenities() != null) {
			List<Amenity> amenities = dto.getAmenities().stream().map(amenityName -> {
				Amenity amenity = new Amenity();
				amenity.setAmenityName(amenityName);
				return amenity;
			}).collect(Collectors.toList());
			entity.setAmenities(amenities);
		} else {
			entity.setAmenities(new ArrayList<>());
		}

		if (dto.getSupportedCategories() != null) {
			List<SupportedCategory> supportedCategories = dto.getSupportedCategories().stream().map(categoryName -> {
				SupportedCategory category = new SupportedCategory();
				category.setCategoryname(categoryName);
				return category;
			}).collect(Collectors.toList());
			entity.setSupportedCategories(supportedCategories);
		}

		if (dto.getAddress() != null) {
			Address address = new Address();
			address.setStreet(dto.getAddress().getStreet());
			address.setPin(dto.getAddress().getPin());

			if (dto.getAddress().getCityName() != null) {
				City city = new City();
				city.setName(dto.getAddress().getCityName());
				address.setCity(city);
			}

			entity.setAddress(address);
		}
		if ("movie".equalsIgnoreCase(dto.getVenueType()) && dto.getScreens() != null) {
			List<Screen> screens = dto.getScreens().stream().map(screenDto -> {
				Screen screen = new Screen();
				screen.setScreenName(screenDto.getScreenName());

				if (screenDto.getLayouts() != null && !screenDto.getLayouts().isEmpty()) {
					List<Layout> layouts = screenDto.getLayouts().stream().map(layoutDto -> {
						Layout layout = new Layout();
						layout.setLayoutName(layoutDto.getLayoutName());
						layout.setCols(layoutDto.getCols());

						if (layoutDto.getRows() != null && !layoutDto.getRows().isEmpty()) {
							List<LayoutRow> layoutRows = layoutDto.getRows().stream().map(rowName -> {
								LayoutRow layoutRow = new LayoutRow();
								layoutRow.setRowName(rowName);
								layoutRow.setLayout(layout);
								return layoutRow;
							}).collect(Collectors.toList());
							layout.setLayoutRows(layoutRows);
						} else {
							layout.setLayoutRows(new ArrayList<>());
						}

						return layout;
					}).collect(Collectors.toList());
					screen.setLayouts(layouts);
				} else {
					screen.setLayouts(new ArrayList<>());
				}

				return screen;
			}).collect(Collectors.toList());
			entity.setScreens(screens);
		}

		return entity;
	}

	@Override
	public VenueDTO createVenue(VenueDTO dto) {
		Venue entity = dtoToEntity(dto);
		if (dto.getAddress() != null) {
			Address address = new Address();
			address.setStreet(dto.getAddress().getStreet());
			address.setPin(dto.getAddress().getPin());

			if (dto.getAddress().getCityName() != null) {
				String cityName = dto.getAddress().getCityName();

				City city = cityRepository.findByName(cityName);

				if (city == null) {
					city = new City();
					city.setName(cityName);
					city.setPopular(false);
					city = cityRepository.save(city);
				}

				address.setCity(city);
			}

			Address savedAddress = addressRepository.save(address);
			entity.setAddress(savedAddress);
		}

		if ("movie".equalsIgnoreCase(entity.getVenueType())) {
			if (entity.getScreens() == null) {
				entity.setScreens(new ArrayList<>());
			}

			for (Screen screen : entity.getScreens()) {
				screen.setVenue(entity);
				if (screen.getLayouts() != null) {
					for (Layout layout : screen.getLayouts()) {
						layout.setScreen(screen);
						if (layout.getLayoutRows() != null) {
							for (LayoutRow layoutRow : layout.getLayoutRows()) {
								layoutRow.setLayout(layout);

								createSeatsForLayoutRow(layoutRow, screen, layout.getCols());
							}
						}
					}
				}
			}
		} else {
			entity.setScreens(new ArrayList<>());
		}

		Venue saved = venueRepository.save(entity);

		eventPublisher.publishEvent(new NotificationEvent(this, "New " + saved.getVenueType() + " Added",
				saved.getVenueName() + " is now available!", "VENUE"));
		return entityToDto(saved);
	}

	private void createSeatsForLayoutRow(LayoutRow layoutRow, Screen screen, int numberOfSeatsPerRow) {
		List<Seat> seats = new ArrayList<>();

		for (int i = 1; i <= numberOfSeatsPerRow; i++) {
			Seat seat = new Seat();
			seat.setSeatNumber(layoutRow.getRowName() + i);
			seat.setReserved(false);
			seat.setLayoutRow(layoutRow);
			seat.setScreen(screen);

			seats.add(seat);
		}

		layoutRow.setSeats(seats);
	}

	@Override
	public List<VenueDTO> getAllVenues() {
		return venueRepository.findAll().stream().filter(data -> !Boolean.TRUE.equals(data.getDeleted()))
				.map(this::entityToDto).collect(Collectors.toList());
	}

	@Override
	public List<VenueDTO> getVenuesByCity(String city) {
		List<Venue> venues = Optional.ofNullable(venueRepository.findByAddress_City_Name(city))
				.orElse(Collections.emptyList());
		return venues.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	@Override
	public boolean softDeleteVenue(Long id) {
		return venueRepository.findById(id).map(venue -> {
			if (Boolean.TRUE.equals(venue.getDeleted())) {
				throw new RuntimeException("Venue already deleted with id: " + id);
			}
			venue.setDeleted(true);
			venueRepository.save(venue);
			return true;
		}).orElseThrow(() -> new VenueNotFoundException("Venue not found with id: " + id));
	}


	@Override
	public List<TimeSlotDTO> getAvailableTimeSlots(Long venueId, Long screenId, LocalDate date) {
	    Venue venue = venueRepository.findById(venueId)
	            .orElseThrow(() -> new RuntimeException("Venue not found"));

	    if ("movie".equalsIgnoreCase(venue.getVenueType())) {
	        if (screenId == null) throw new IllegalArgumentException("ScreenId is required for movie venues");

	        List<Show> shows = showRepository.findByVenueIdAndScreenId(venueId, screenId);

	        if (shows.isEmpty()) return List.of(fullDaySlot());

	        List<ShowTimeWrapper> allShowTimes = new ArrayList<>();
	        for (Show show : shows) {
	            show.getShowstimedate().stream()
	                    .filter(std -> std.getShowDate().equals(date))
	                    .forEach(std -> std.getShowTimes().forEach(st ->
	                            allShowTimes.add(new ShowTimeWrapper(show, st))
	                    ));
	        }

	        if (allShowTimes.isEmpty()) return List.of(fullDaySlot());

	        allShowTimes.sort(Comparator.comparing(stw -> stw.showTime.getShowTime()));

	        List<TimeSlotDTO> freeSlots = new ArrayList<>();
	        LocalTime prevEnd = LocalTime.of(0, 0);
	        int buffer = 30;

	        for (ShowTimeWrapper stw : allShowTimes) {
	            LocalTime start = stw.showTime.getShowTime();
	            int duration = parseRuntimeToMinutes(stw.show.getEvent().getRunTime());

	            if (prevEnd.isBefore(start)) {
	                freeSlots.add(new TimeSlotDTO(null, prevEnd, start)); // id=null for DTO
	            }

	            prevEnd = start.plusMinutes(duration + buffer);
	            if (prevEnd.isAfter(LocalTime.of(23, 59))) prevEnd = LocalTime.of(23, 59);
	        }

	        if (prevEnd.isBefore(LocalTime.of(23, 59))) {
	            freeSlots.add(new TimeSlotDTO(null, prevEnd, LocalTime.of(23, 59, 59)));
	        }

	        return freeSlots;
	    }

	    List<Show> shows = showRepository.findByVenueId(venueId);
	    if (shows.isEmpty()) return List.of(fullDaySlot());

	    List<TimeSlotDTO> freeSlots = new ArrayList<>();
	    for (Show show : shows) {
	        freeSlots.addAll(getAvailableSlotsForShow(show, date));
	    }

	    return freeSlots.stream()
	            .distinct()
	            .sorted(Comparator.comparing(TimeSlotDTO::getStartTime))
	            .collect(Collectors.toList());
	}

	private TimeSlotDTO fullDaySlot() {
	    return new TimeSlotDTO(null, LocalTime.of(0, 0), LocalTime.of(23, 59, 59));
	}

	private static class ShowTimeWrapper {
	    Show show;
	    ShowTime showTime;

	    ShowTimeWrapper(Show show, ShowTime showTime) {
	        this.show = show;
	        this.showTime = showTime;
	    }
	}

	private List<TimeSlotDTO> getAvailableSlotsForShow(Show show, LocalDate date) {
	    Optional<ShowTimeDate> showTimeDateOpt = show.getShowstimedate().stream()
	            .filter(std -> std.getShowDate().equals(date))
	            .findFirst();

	    if (showTimeDateOpt.isEmpty()) return List.of(fullDaySlot());

	    ShowTimeDate showTimeDate = showTimeDateOpt.get();
	    List<ShowTime> showTimes = new ArrayList<>(showTimeDate.getShowTimes());
	    showTimes.sort(Comparator.comparing(ShowTime::getShowTime));

	    List<TimeSlotDTO> freeSlots = new ArrayList<>();
	    LocalTime prevEnd = LocalTime.of(0, 0);
	    int buffer = 30;

	    for (ShowTime st : showTimes) {
	        LocalTime start = st.getShowTime();
	        int duration = parseRuntimeToMinutes(show.getEvent().getRunTime());

	        if (prevEnd.isBefore(start)) {
	            freeSlots.add(new TimeSlotDTO(null, prevEnd, start));
	        }

	        prevEnd = start.plusMinutes(duration + buffer);
	        if (prevEnd.isAfter(LocalTime.of(23, 59))) prevEnd = LocalTime.of(23, 59);
	    }

	    if (prevEnd.isBefore(LocalTime.of(23, 59))) {
	        freeSlots.add(new TimeSlotDTO(null, prevEnd, LocalTime.of(23, 59, 59)));
	    }

	    return freeSlots;
	}













	@Override
	public VenueDTO updateVenue(Long venueId, VenueDTO dto) {
		Venue existingVenue = venueRepository.findById(venueId)
				.orElseThrow(() -> new RuntimeException("Venue not found:"));

		existingVenue.setVenueName(dto.getVenueName());
		existingVenue.setVenueType(dto.getVenueType());

		if (dto.getAddress() != null) {
			Address address = existingVenue.getAddress();
			if (address == null) {
				address = new Address();
			}
			address.setStreet(dto.getAddress().getStreet());
			address.setPin(dto.getAddress().getPin());

			if (dto.getAddress().getCityName() != null) {
				String cityName = dto.getAddress().getCityName();
				City city = cityRepository.findByName(cityName);
				if (city == null) {
					city = new City();
					city.setName(cityName);
					city = cityRepository.save(city);
				}
				address.setCity(city);
			}

			Address savedAddress = addressRepository.save(address);
			existingVenue.setAddress(savedAddress);
		}

		if ("movie".equalsIgnoreCase(existingVenue.getVenueType())) {
			if (dto.getScreens() != null) {
				List<Screen> updatedScreens = new ArrayList<>();
				for (ScreenDTO screenDto : dto.getScreens()) {
					Screen screen = screenDto.getId() != null
							? screenRepository.findById(screenDto.getId()).orElse(new Screen())
							: new Screen();

					screen.setVenue(existingVenue);

					if (screenDto.getLayouts() != null) {
						List<Layout> updatedLayouts = new ArrayList<>();
						for (LayoutDTO layoutDto : screenDto.getLayouts()) {
							Layout layout;
							if (layoutDto.getId() != null) {
								layout = layoutRepository.findById(layoutDto.getId()).orElse(new Layout());
							} else {
								layout = new Layout();
							}
							layout.setScreen(screen);

							if (layoutDto.getRows() != null) {
								List<LayoutRow> updatedRows = new ArrayList<>();
								for (String rowName : layoutDto.getRows()) {
									LayoutRow row = new LayoutRow();
									row.setLayout(layout);
									row.setRowName(rowName);
									updatedRows.add(row);
								}
								layout.setLayoutRows(updatedRows);
							}
							updatedLayouts.add(layout);
						}
						screen.setLayouts(updatedLayouts);
					}
					updatedScreens.add(screen);
				}
				existingVenue.setScreens(updatedScreens);
			} else {
				existingVenue.setScreens(new ArrayList<>());
			}
		} else {
			existingVenue.setScreens(new ArrayList<>());
		}

		Venue saved = venueRepository.save(existingVenue);

		eventPublisher.publishEvent(
				new NotificationEvent(this, "Venue Updated", saved.getVenueName() + " has been updated.", "VENUE"));

		return entityToDto(saved);
	}

	private int parseRuntimeToMinutes(String runTime) {
		if (runTime == null || runTime.isBlank())
			throw new IllegalArgumentException("Runtime is missing for this event.");

		runTime = runTime.toLowerCase().trim();

		int hours = 0;
		int minutes = 0;

		try {
			if (runTime.matches(".*\\d+\\s*h.*")) {
				String hrPart = runTime.split("h|hr")[0].replaceAll("[^0-9]", "").trim();
				if (!hrPart.isEmpty())
					hours = Integer.parseInt(hrPart);
			}

			if (runTime.matches(".*\\d+\\s*m.*")) {
				String minPart = runTime.substring(runTime.lastIndexOf("h") + 1).replaceAll("[^0-9]", "").trim();
				if (!minPart.isEmpty())
					minutes = Integer.parseInt(minPart);
			} else if (runTime.contains("minute")) {
				String num = runTime.replaceAll("[^0-9]", "").trim();
				if (!num.isEmpty())
					minutes = Integer.parseInt(num);
			} else if (runTime.matches("\\d+")) {
				minutes = Integer.parseInt(runTime);
			}

		} catch (Exception e) {
			throw new RuntimeException("Invalid runtime format in DB: " + runTime, e);
		}

		return (hours * 60) + minutes;
	}

	@Override
	public VenueDTO getVenueById(Long id) {
		Optional<Venue> optionalVenue = venueRepository.findById(id);

		return optionalVenue.map(this::entityToDto).orElse(null);
	}

}