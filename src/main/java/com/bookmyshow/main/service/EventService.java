package com.bookmyshow.main.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.dto.CategoryDTO;
import com.bookmyshow.main.dto.DateFilterDTO;
import com.bookmyshow.main.dto.EventDTO;
import com.bookmyshow.main.dto.EventResponseDto;
import com.bookmyshow.main.dto.EventResponseDtoCard;
import com.bookmyshow.main.dto.EventSearchDTO;
import com.bookmyshow.main.dto.FormatDTO;
import com.bookmyshow.main.dto.GenresDTO;
import com.bookmyshow.main.dto.LanguagesDTO;
import com.bookmyshow.main.dto.MoreFilterDTO;
import com.bookmyshow.main.dto.PriceDTO;
import com.bookmyshow.main.dto.ReleaseMonthDTO;
import com.bookmyshow.main.dto.TagDTO;

public interface EventService {
	EventDTO createEvent(EventDTO movieDto, 
			MultipartFile poster, 
			List<MultipartFile> castImages,
			List<MultipartFile> crewImages
			) throws IOException;

	EventResponseDto getEventById(Long id);

	

	List<EventSearchDTO> searchEventNames(String name, List<String> eventTypes);

	EventDTO updateEvent(Long id,Long adminid, EventDTO movieDto, MultipartFile poster, List<MultipartFile> castImages,
			List<MultipartFile> crewImages)
			throws IOException;

	boolean deleteEvent(Long id,Long adminid);



	
	

	
	Page<EventResponseDtoCard> filterEvents(
		    String type,
		    Integer cityid,
		    Integer adminId,
		    List<Integer> languages,
		    List<Integer> genres,
		    List<Integer> formats,
		    List<Integer> tags,
		    List<Integer> categories,
		    List<Integer> price,
		    List<Integer> moreFilters,
		    List<Integer> releaseMonths,
		    List<Integer> dateFilters,
		    int page,
		    int size,
		    boolean includeCurrentlyPlaying  // new param
		);
	
	List<LanguagesDTO> getAllLanguages(String eventType);

	List<GenresDTO> getAllGenres(String eventType);

	 List<FormatDTO> getAllFormats();
	 List<TagDTO> getAllTags();
	 List<ReleaseMonthDTO> getAllReleaseMonths();
	 List<DateFilterDTO> getAllDateFilters();
	 List<CategoryDTO> getAllCategories(String eventType);
	 List<PriceDTO> getAllPrices();
	 List<MoreFilterDTO> getAllMoreFilters(String eventType);


	List<EventResponseDtoCard> getPopularEvents(String eventType);



}