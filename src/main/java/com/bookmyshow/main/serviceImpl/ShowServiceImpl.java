package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.dto.SupportedCategoryDTO;
import com.bookmyshow.main.model.*;
import com.bookmyshow.main.repository.*;
import com.bookmyshow.main.service.ShowService;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ScreenRepository screenRepository;
    
    @Autowired
    private LanguagesRepository languagesRepository;



    @Override
    @Transactional
    public ShowRequestDTO createShow(ShowRequestDTO dto) {
        Show show = new Show();

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id " + dto.getEventId()));
        show.setEvent(event);

        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found with id " + dto.getVenueId()));
        show.setVenue(venue);

        if (dto.getScreenName() != null) {
            Screen screen = screenRepository.findByVenueAndScreenName(venue, dto.getScreenName())
                .orElseThrow(() -> new RuntimeException("Screen not found"));
            show.setScreen(screen);
        }

        show.setEventType(dto.getEventType());
        show.setCity(dto.getCity());
        show.setDate(dto.getDate());
        show.setStartTime(dto.getStartTime());
        show.setDuration(dto.getDuration());
        show.setStatus(dto.getStatus());
        show.setFormat(dto.getFormat());

        if (dto.getLanguageName() != null) {
            List<Languages> languages = dto.getLanguageName().stream()
                .map(name -> {
                    return languagesRepository.findByLanguageName(name)
                            .orElseThrow(() -> new RuntimeException("Language not found: " + name));
                }).collect(Collectors.toList());
            show.setLanguages(languages);
        }


        Show savedShow = showRepository.save(show);

        return convertEntityToDto(savedShow);
    }

    private ShowRequestDTO convertEntityToDto(Show show) {
        ShowRequestDTO dto = new ShowRequestDTO();
        dto.setShowId(show.getId());
        dto.setEventId(show.getEvent().getEventId());
        dto.setVenueId(show.getVenue().getId());
        dto.setEventType(show.getEventType());
        dto.setCity(show.getCity());
        dto.setDate(show.getDate());
        dto.setStartTime(show.getStartTime());
        dto.setDuration(show.getDuration());
        dto.setStatus(show.getStatus());
        dto.setFormat(show.getFormat());
        dto.setScreenName(show.getScreen() != null ? show.getScreen().getScreenName() : null);

        dto.setLanguageName(show.getLanguages().stream()
                .map(Languages::getLanguageName)
                .collect(Collectors.toList()));


        return dto;
    }
    
    
}
