package com.bookmyshow.main.service;

import java.util.List;

import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.VenueShowDTO;

public  interface ShowService {

    List<VenueShowDTO> getShows(ShowRequestDTO request);

 
	
}
