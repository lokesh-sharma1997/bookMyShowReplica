package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.VenueShowDTO;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.ShowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VenueShowDTO>>> getShows(
            @RequestParam Long eventId,
            @RequestParam String date) {

        ShowRequestDTO request = new ShowRequestDTO();
        request.setEventId(eventId);
        request.setDate(date);

        List<VenueShowDTO> shows = showService.getShows(request);

        ApiResponse<List<VenueShowDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Show fetched successfully",
                true,
                shows
        );

        return ResponseEntity.ok(response);
    }
}
