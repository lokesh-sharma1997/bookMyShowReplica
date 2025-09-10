package com.bookmyshow.main.response;

import java.util.List;

import com.bookmyshow.main.dto.StateDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StateResponse {
private List<StateDto> stateDto;
}
