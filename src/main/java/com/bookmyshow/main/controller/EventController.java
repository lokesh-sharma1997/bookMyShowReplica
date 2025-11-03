package com.bookmyshow.main.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.dto.CategoryDTO;
import com.bookmyshow.main.dto.DateFilterDTO;
import com.bookmyshow.main.dto.EventDTO;
import com.bookmyshow.main.dto.EventFilterRequest;
import com.bookmyshow.main.dto.EventResponseDto;
import com.bookmyshow.main.dto.EventResponseDtoCard;
import com.bookmyshow.main.dto.EventSearchDTO;
import com.bookmyshow.main.dto.EventSearchRequestDto;
import com.bookmyshow.main.dto.FormatDTO;
import com.bookmyshow.main.dto.GenresDTO;
import com.bookmyshow.main.dto.LanguagesDTO;
import com.bookmyshow.main.dto.MoreFilterDTO;
import com.bookmyshow.main.dto.PriceDTO;
import com.bookmyshow.main.dto.ReleaseMonthDTO;
import com.bookmyshow.main.dto.TagDTO;
import com.bookmyshow.main.exception.EventCustomException;
import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.repository.EventRepository;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.EventService;
import com.bookmyshow.main.specification.EventSpecification;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;


@RestController
@RequestMapping("/api/events")
@Tag(name = "event Controller", description = "Manage events in BookMyShow app")
public class EventController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EventService eventService;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private Validator validator;

	@Operation(summary = "${event.createEvent}")
	@PostMapping(value = "/create-event", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ApiResponse<Void>> createEvent(@RequestPart("event") String eventJson,
			@RequestPart("poster") MultipartFile poster,
			@RequestPart(value = "castImages", required = false) List<MultipartFile> castImages,
			@RequestPart(value = "crewImages", required = false) List<MultipartFile> crewImages) throws IOException {
		long posterMaxlength =  2 * 1024 * 1024;   // 2Mb

		if (poster == null || poster.isEmpty()) {
			throw new EventCustomException("Poster size is required for creating an event");
			
		}
		if(poster.getSize()>posterMaxlength)
		{
			throw new EventCustomException("Poster image must be minimum to 2Mb");
		}

		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		EventDTO eventDto = objectMapper.readValue(eventJson, EventDTO.class);
		 Set<ConstraintViolation<EventDTO>> violations = validator.validate(eventDto);
		    if (!violations.isEmpty()) {
		        String errors = violations.stream()
		                .map(ConstraintViolation::getMessage)
		                .collect(Collectors.joining("; "));
		        throw new EventCustomException(errors);
		    }
		if (eventDto.getName() == null || eventDto.getName().isBlank()) {
			throw new EventCustomException("Event name must not be empty");
		}

		eventService.createEvent(eventDto, poster, castImages, crewImages);

		ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Event created successfully", true,
				null);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Get event by ID")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EventResponseDto>> getEventById(@PathVariable Long id) {

		ApiResponse<EventResponseDto> response = new ApiResponse<>(HttpStatus.CREATED.value(),
				"Event fetched successfully", true, eventService.getEventById(id));
		EventResponseDto event = eventService.getEventById(id);
		if (event == null) {
			throw new EventCustomException("Event not found with id: " + id);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Search events by partial name")
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<List<EventSearchDTO>>> searchEventNames(
			@RequestBody EventSearchRequestDto request) {

		// Call the service layer to get the event names
		List<EventSearchDTO> eventNames = eventService.searchEventNames(request.getName(), request.getEventTypes());
		String message;
		boolean flag;
		if (eventNames.isEmpty() || eventNames == null) {
			message = "event not found";
			flag = false;
		} else {
			message = "Event names fetched successfully";
			flag = true;
		}
		// Create ApiResponse with event names
		ApiResponse<List<EventSearchDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), message, flag,
				eventNames);

		// Return the ResponseEntity with the ApiResponse
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Update a event")
	@PutMapping(value = "/update/{id}/{adminId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<Void>> updateEvent(@PathVariable Long id,@PathVariable Long adminId ,@RequestPart("Event") String eventJson,
			@RequestPart(value = "poster", required = false) MultipartFile poster,
			@RequestPart(value = "castImages", required = false) List<MultipartFile> castImages,
			@RequestPart(value = "crewImages", required = false) List<MultipartFile> crewImages

	) throws IOException {

		EventDTO eventDto = objectMapper.readValue(eventJson, EventDTO.class);

		eventService.updateEvent(id,adminId, eventDto, poster, castImages, crewImages);
		ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Event update successfully", true,
				null);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "${event.getPopularEvents}")
	@GetMapping("/get-popular-events")
	public ResponseEntity<ApiResponse<List<EventResponseDtoCard>>> getPopularEvents(
			@RequestParam(required = false) String eventType) {
		List<EventResponseDtoCard> popularEvents = eventService.getPopularEvents(eventType);

		ApiResponse<List<EventResponseDtoCard>> response = new ApiResponse<>(HttpStatus.CREATED.value(),
				"Popolar Events fetch  successfully", true, popularEvents);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Delete a event")
	@PatchMapping("/delete/{id}/{adminId}")
	public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable Long id,@PathVariable Long adminId) {
		boolean deleted = eventService.deleteEvent(id,adminId);

		ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Event deleted successfully", true,
				null);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Event filter with pagination")
	@PostMapping("/filter")
	public ResponseEntity<ApiResponse<Map<String, Object>>> filterEvents(@RequestBody EventFilterRequest filterRequest,
			@RequestParam int page, @RequestParam int size,
			@RequestParam(required = false, defaultValue = "true") boolean upcomingMovie
			,@RequestParam(required = false) Integer adminId) {

		Specification<Event> spec = EventSpecification.filterEvents(filterRequest.getType(),
				filterRequest.getCityid(),adminId,
				filterRequest.getLanguages(), filterRequest.getGenres(), filterRequest.getFormats(),
				filterRequest.getTags(), filterRequest.getCategories(), filterRequest.getPrice(),
				filterRequest.getMorefilter(), filterRequest.getReleaseMonths(), filterRequest.getDateFilters());

		Long count;

		if ("Movie".equalsIgnoreCase(filterRequest.getType()) && !upcomingMovie) {
			count = eventRepository.findAll(spec).stream()
					.filter(event -> !event.getDeleted() && !event.getCurrentlyPlaying()).count();
		} else if ("Movie".equalsIgnoreCase(filterRequest.getType())) {
			count = eventRepository.findAll(spec).stream()
					.filter(event -> !event.getDeleted() && event.getCurrentlyPlaying()).count();
		}

		else {

			count = eventRepository.findAll(spec).stream().filter(event -> !event.getDeleted()).count();
		}

		Page<EventResponseDtoCard> eventsPage = eventService.filterEvents(filterRequest.getType(),
				filterRequest.getCityid(),adminId,
				filterRequest.getLanguages(), filterRequest.getGenres(), filterRequest.getFormats(),
				filterRequest.getTags(), filterRequest.getCategories(), filterRequest.getPrice(),
				filterRequest.getMorefilter(), filterRequest.getReleaseMonths(), filterRequest.getDateFilters(), page,
				size, upcomingMovie);

		Map<String, Object> data = new HashMap<>();
		data.put("content", eventsPage.getContent());
		data.put("count", count);

		ApiResponse<Map<String, Object>> response = new ApiResponse<>(HttpStatus.OK.value(),
				eventsPage.isEmpty() ? "No events found for the given filters." : "Events filtered successfully",
				!eventsPage.isEmpty(), data);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(summary = "Get All languages")
	@GetMapping("/languages")
	public ResponseEntity<ApiResponse<List<LanguagesDTO>>> getLanguages(@RequestParam String eventType) {

		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all languages successfully", true,
				eventService.getAllLanguages(eventType)));
	}

	@Operation(summary = "Get All Genres")
	@GetMapping("/genres")
	public ResponseEntity<ApiResponse<List<GenresDTO>>> getGenres(@RequestParam String eventType) {

		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all Genres successfully", true,
				eventService.getAllGenres(eventType)));
	}

	@Operation(summary = "Get All Formats")
	@GetMapping("/formats")
	public ResponseEntity<ApiResponse<List<FormatDTO>>> getFormats() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all Formats successfully", true,
				eventService.getAllFormats()));
	}

	@Operation(summary = "Get All Tags")
	@GetMapping("/tags")
	public ResponseEntity<ApiResponse<List<TagDTO>>> getTags() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all Tags successfully", true,
				eventService.getAllTags()));
	}

	@Operation(summary = "Get All Release Months")
	@GetMapping("/release-months")
	public ResponseEntity<ApiResponse<List<ReleaseMonthDTO>>> getReleaseMonths() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all Release Months successfully", true,
				eventService.getAllReleaseMonths()));
	}

	@Operation(summary = "Get All Date Filters")
	@GetMapping("/date-filters")
	public ResponseEntity<ApiResponse<List<DateFilterDTO>>> getDateFilters() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all Date Filters successfully", true,
				eventService.getAllDateFilters()));
	}

	@Operation(summary = "Get All Categories")
	@GetMapping("/categories")
	public ResponseEntity<ApiResponse<List<CategoryDTO>>> getCategories(@RequestParam String eventType) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all Categories successfully", true,
				eventService.getAllCategories(eventType)));
	}

	@Operation(summary = "Get All More Filters")
	@GetMapping("/more-filters")
	public ResponseEntity<ApiResponse<List<MoreFilterDTO>>> getMoreFilters(@RequestParam String eventType) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all More Filters successfully", true,
				eventService.getAllMoreFilters(eventType)));
	}

	@Operation(summary = "Get All Prices")
	@GetMapping("/prices")
	public ResponseEntity<ApiResponse<List<PriceDTO>>> getPrices() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Fetch all Prices successfully", true,
				eventService.getAllPrices()));
	}

}