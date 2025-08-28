package com.bookmyshow.main.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bookmyshow.main.dto.EventDto;
import com.bookmyshow.main.dto.EventFilterRequest;
import com.bookmyshow.main.dto.EventResponseDto;
import com.bookmyshow.main.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/events")
@Tag(name = "event Controller", description = "Manage events in BookMyShow app")
public class EventController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventService eventService;

    @Operation(summary = "Create a new event", description = "Add a new event with poster image")
    @ApiResponse(responseCode = "200", description = "event created successfully")
    @PostMapping(value="/create-event", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventDto> createEvent(
    		@RequestPart("event") String eventJson,
            @RequestPart("poster") MultipartFile poster,
            @RequestPart(value = "castImages", required = false) List<MultipartFile> castImages) throws IOException, java.io.IOException {

    	EventDto eventDto = objectMapper.readValue(eventJson, EventDto.class);
        return ResponseEntity.ok(eventService.createEvent(eventDto, poster,castImages));
    }

    @Operation(summary = "Get event by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @Operation(summary = "Get event by name")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<EventDto> getEventByName(@PathVariable String name,@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(eventService.getEventByName(name,contentType));
    }
    
    @Operation(summary = "Get all event")
    @GetMapping("/get-all-events")
        public ResponseEntity<List<EventResponseDto>> getEventsByType(
                @RequestParam(required = false) String contentType) {
            List<EventResponseDto> events = eventService.getAllEventByType(contentType);
            return ResponseEntity.ok(events);
        }
    


    @Operation(summary = "Update a event")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable Long id,
            @RequestPart("Event") String eventJson,
            @RequestPart(value = "poster", required = false) MultipartFile poster,
            @RequestPart(value = "castImages", required = false) List<MultipartFile> castImages
    ) throws IOException {
        // JSON string ko MovieDto me convert karna
    	EventDto eventDto = objectMapper.readValue(eventJson, EventDto.class);
        return ResponseEntity.ok(eventService.createEvent(eventDto, poster,castImages));
    }



    @Operation(summary = "Delete a event")
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
    	eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "event filter")
    @GetMapping("/filter")
    public List<EventDto> filterEvent(@RequestBody EventFilterRequest filterRequest) {
        return eventService.filterEvents(
                filterRequest.getLanguages(),
                filterRequest.getGenres(),
                filterRequest.getFormats(),
                filterRequest.getReleaseMonth()
        );
    }

    @Operation(summary = "Get All languages")
    @GetMapping("/languages")
    public ResponseEntity<List<String>> getLanguages(@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(eventService.getAllLanguages(contentType));
    }
    @Operation(summary = "Get All Genres")
    @GetMapping("/genres")
    public ResponseEntity<List<String>> getGenres(@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(eventService.getAllGenres(contentType));
    }
    @Operation(summary = "Get All Formats")
    @GetMapping("/formats")
    public ResponseEntity<List<String>> getFormats(@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(eventService.getAllFormats(contentType));
    }

}