package com.bookmyshow.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.ShowResponseDTO;
import com.bookmyshow.main.service.ShowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    // GET API: /api/shows?eventId=1&date=2025-10-05
    @GetMapping
    public ShowResponseDTO getShows(
            @RequestParam Long eventId,
            @RequestParam String date) {

        ShowRequestDTO request = new ShowRequestDTO();
        request.setEventId(eventId);
        request.setDate(date);

        return showService.getShows(request);
    }
}
