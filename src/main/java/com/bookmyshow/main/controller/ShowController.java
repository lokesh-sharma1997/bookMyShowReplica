package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.ShowRequestDTO;
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

    @PostMapping("/create-show")
    public ResponseEntity<ApiResponse<Void>> createShow(@RequestBody ShowRequestDTO showRequestDTO) {
        showService.createShow(showRequestDTO);
        
        ApiResponse<Void> response = new ApiResponse<>(
	            HttpStatus.CREATED.value(),
	            "Show created successfully",
	            true,
	            null
	    );

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    


}
