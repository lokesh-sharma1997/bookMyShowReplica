package com.bookmyshow.main.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.StateDto;
import com.bookmyshow.main.repository.StateRepository;
import com.bookmyshow.main.service.StateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {
	private final StateRepository stateRepository;

    //Returns All States
	@Override
	public List<StateDto> getAllStates() {
		return stateRepository.findAll().stream().map(state -> new StateDto(state.getStatekey(), state.getName()))
				.collect(Collectors.toList());
	}

}
