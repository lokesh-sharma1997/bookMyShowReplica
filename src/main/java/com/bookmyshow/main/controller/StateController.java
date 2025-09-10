package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.StateDto;
import com.bookmyshow.main.exception.StateNotFoundException;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.StateResponse;
import com.bookmyshow.main.service.StateService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/states")
public class StateController {

	private final StateService stateService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${user.getAllStates}")
	public ResponseEntity<ApiResponse<StateResponse>> getAllStates() {
		List<StateDto> states = stateService.getAllStates();
		if (states.isEmpty()) {
			throw new StateNotFoundException("No states found");
		}
		StateResponse stateResponse = new StateResponse(states);
		return ResponseEntity.ok(new ApiResponse<>(200, "All states retrieved", true, stateResponse));
	}
}
