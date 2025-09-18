package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.ShowRequestDTO;

public  interface ShowService {

    
	ShowRequestDTO createShow(ShowRequestDTO showDTO);
//	ShowRequestDTO updateShow(Long showId, ShowRequestDTO dto);
	
}
