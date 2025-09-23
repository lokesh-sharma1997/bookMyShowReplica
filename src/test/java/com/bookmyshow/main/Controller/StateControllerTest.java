package com.bookmyshow.main.Controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.bookmyshow.main.controller.StateController;
import com.bookmyshow.main.dto.StateDto;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.StateResponse;
import com.bookmyshow.main.service.StateService;

@ExtendWith(MockitoExtension.class)
public class StateControllerTest {

	@Mock
	private StateService stateService;
	@InjectMocks
	private StateController stateController;

	private StateDto state_dto;

	@BeforeEach
	void setUp() {
		state_dto = new StateDto();
		state_dto.setStateKey("UP");
		state_dto.setName("Uttar Pradesh");

	}

	@Test
	void testGetAllStates() {
		StateDto state_dto2 = new StateDto();
		state_dto2.setStateKey("UP");
		state_dto2.setName("Uttar Pradesh");
		List<StateDto> states = Arrays.asList(state_dto, state_dto2);
		when(stateService.getAllStates()).thenReturn(states);
		ResponseEntity<ApiResponse<StateResponse>> result = stateController.getAllStates();
		assertEquals(200, result.getBody().getStatusCode());

		ApiResponse<StateResponse> response = result.getBody();
		assert response != null;
		assertEquals("All states retrieved", response.getMessage());
		assertEquals(2, response.getData().getStateDto().size());
	}

	@Test
	void testGetAllStates_WhenNoStatesFound() {

		when(stateService.getAllStates()).thenReturn(Arrays.asList());
		ResponseEntity<ApiResponse<StateResponse>> result = stateController.getAllStates();
		assertEquals(200, result.getBody().getStatusCode());
		ApiResponse<StateResponse> response = result.getBody();
		assert response != null;
		assertEquals("No states found", response.getMessage()); 
		assertEquals(0, response.getData().getStateDto().size());
	}

}
