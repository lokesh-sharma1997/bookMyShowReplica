package com.bookmyshow.main.response;

import java.util.List;

import com.bookmyshow.main.dto.VenueDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueListResponse {
	 private List<VenueDTO> venues;
	    private long totalCount;
}
