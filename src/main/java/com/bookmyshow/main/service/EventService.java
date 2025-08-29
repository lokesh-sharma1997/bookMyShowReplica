package com.bookmyshow.main.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.dto.EventDto;
import com.bookmyshow.main.dto.EventResponseDto;

public interface EventService {
	EventDto createEvent(EventDto movieDto, MultipartFile poster,List<MultipartFile> castImages) throws IOException;
	EventDto getEventById(Long id,String contentType);
	EventDto getEventByName(String name,String contentType);
//    List<EventDto> getAllEventByType(String contentType);
    EventDto updateEvent(Long id, EventDto movieDto, MultipartFile poster, List<MultipartFile> castImages) throws IOException;

    void deleteEvent(Long id);
    List<EventDto> filterEvents(  List<String> languages,
            List<String> genres,
            List<String> formats,
            String releaseMonth);
    List<String> getAllLanguages(String contentType);
    List<String> getAllGenres(String contentType);
    List<String> getAllFormats(String contentType);
    List<EventResponseDto> getAllEventByType(String contentType);
	
    public List<EventResponseDto> getPopularEvents(String contentType);
    
}

