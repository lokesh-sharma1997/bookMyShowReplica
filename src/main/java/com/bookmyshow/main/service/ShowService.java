package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.ShowResponseDTO;

public  interface ShowService {

	ShowResponseDTO getShows(ShowRequestDTO request);

 
	
}
