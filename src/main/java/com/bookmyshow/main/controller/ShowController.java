package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.ShowDTO;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/create/show")
    public ResponseEntity<ApiResponse<Long>> createShow(@RequestBody ShowDTO showDTO) {
        Long showId = showService.createShow(showDTO);
        ApiResponse<Long> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Show created successfully",
                true,
                showId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
