package com.bookmyshow.main.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookmyshow.main.dto.StateDto;
import com.bookmyshow.main.model.State;
import com.bookmyshow.main.repository.StateRepository;

@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {

	@Mock
	private StateRepository stateRepository;

	@InjectMocks
	private StateServiceImpl stateService;

	@Test
	void testGetAllStates() {
		State state1 = new State();
		state1.setStatekey("UP");
		state1.setName("UttarPradesh");

		State state2 = new State();
		state2.setStatekey("UK");
		state2.setName("Uttrakhand");

		when(stateRepository.findAll()).thenReturn(Arrays.asList(state1, state2));

		List<StateDto> result = stateService.getAllStates();

		 
		assertEquals(2, result.size());
		assertEquals("UttarPradesh", result.get(0).getName());
		assertEquals("UP", result.get(0).getStateKey());
		assertEquals("Uttrakhand", result.get(1).getName());
		assertEquals("UK", result.get(1).getStateKey());
	}
}