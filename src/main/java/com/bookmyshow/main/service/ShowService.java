package com.bookmyshow.main.service;

import java.util.List;

import com.bookmyshow.main.dto.ShowFetchDTO;
import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.ShowResponseDTO;

public  interface ShowService {

    
	ShowRequestDTO createShow(ShowRequestDTO showDTO);
//	ShowRequestDTO updateShow(Long showId, ShowRequestDTO dto);

	ShowResponseDTO getShows(ShowFetchDTO request);


	
}
