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
    public ResponseEntity<ApiResponse<ShowRequestDTO>> createShow(@RequestBody ShowRequestDTO showRequestDTO) {
        showService.createShow(showRequestDTO);
        
        ApiResponse<ShowRequestDTO> response = new ApiResponse<>();
        response.setMessage("Show created successfully");
        response.setSuccess(true);
        response.setData(showRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
//    @PutMapping("/update-show/{id}")
//    public ResponseEntity<ApiResponse<ShowRequestDTO>> updateShow(
//            @PathVariable Long id,
//            @RequestBody ShowRequestDTO showRequestDTO) {
//
//        ShowRequestDTO updatedShow = showService.updateShow(id, showRequestDTO);
//
//        ApiResponse<ShowRequestDTO> response = new ApiResponse<>();
//        response.setMessage("Show updated successfully");
//        response.setSuccess(true);
//        response.setData(updatedShow);
//
//        return ResponseEntity.ok(response);
//    }

    


}
