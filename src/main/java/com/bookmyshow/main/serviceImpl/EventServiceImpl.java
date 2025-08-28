package com.bookmyshow.main.serviceImpl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.dto.CastDto;
import com.bookmyshow.main.dto.EventDto;
import com.bookmyshow.main.dto.EventResponseDto;
import com.bookmyshow.main.model.Cast;
import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.repository.EventRepository;
import com.bookmyshow.main.security.SecurityConfig;
import com.bookmyshow.main.service.EventService;
import com.bookmyshow.main.specification.MovieSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class EventServiceImpl implements EventService {

    private final SecurityConfig securityConfig;
	@Autowired
	private ObjectMapper objectMapper;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper mapper;

    EventServiceImpl(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    private EventDto toDto(Event movie) { return mapper.map(movie, EventDto.class); }
    private Event toEntity(EventDto dto) { return mapper.map(dto, Event.class); }

    
    

    @Override
    public EventDto createEvent(EventDto eventDto, MultipartFile poster, List<MultipartFile> castImages) throws IOException {
        // Poster ko base64 me convert karke movie me set karna
        String base64Image = Base64.getEncoder().encodeToString(poster.getBytes());
        Event event = toEntity(eventDto);
        event.setImageurl(base64Image);

        String eventType = eventDto.getContentType();
        Set<String> predefined = new HashSet<>(Arrays.asList("Movie", "Show", "Cartoon", "Event"));
        event.setContentType(predefined.contains(eventType) ? eventType : eventType);

        // Cast images handle karna
        if (castImages != null && eventDto.getCast() != null) {
            for (int i = 0; i < eventDto.getCast().size(); i++) {
                if (i < castImages.size()) {
                    String base64 = Base64.getEncoder().encodeToString(castImages.get(i).getBytes());
                    eventDto.getCast().get(i).setImg(base64);
                }
            }
        }

        // ✅ Ensure cast bhi entity me map ho
        event.setCast(
        		eventDto.getCast().stream()
        	        .map((CastDto c) -> {       
        	            Cast cast = new Cast();
        	            cast.setActorName(c.getActorName());
        	            cast.setImg(c.getImg());
        	            return cast;
        	        })
        	        .collect(Collectors.toList())
        	);


        return toDto(eventRepository.save(event));
    }

    @Override
    public EventDto getEventById(Long id, String contentType) {
       

        return eventRepository.findById(id)
                .filter(movie -> !movie.getDeleted() &&
                        (contentType == null || contentType.isEmpty() || contentType.equalsIgnoreCase(movie.getContentType())))
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }




    @Override
    public EventDto getEventByName(String name,String contentType) {
    	Event event = eventRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getDeleted()) {
            throw new RuntimeException("Event is deleted");
        }

        return toDto(event);
    }


    @Override
    public List<EventResponseDto> getAllEventByType(String contentType) {
        return eventRepository.findAll().stream()
                .filter(event -> !event.getDeleted() &&
                        (contentType == null || contentType.isEmpty()
                         || contentType.equalsIgnoreCase(event.getContentType())))
                .map(event -> {
                    EventResponseDto dto = new EventResponseDto();

                    // movieId
                    dto.setEventId(event.getId());

                    // title
                    dto.setTitle(event.getName());

                    // likes
                    dto.setLikes(event.getLikes() != null ? event.getLikes().toString() : "0");

                    // poster
                    dto.setPoster(event.getImageurl());

                    // ✅ genre (already List<String> in Event entity)
                    dto.setGenre(event.getGenre() != null ? event.getGenre() : new ArrayList<>());

                    // imdbVotes (placeholder)
                    dto.setImdbVotes("0");

                    // imdbRating
                    dto.setImdbRating(event.getRating() != null
                            ? event.getRating().toString()
                            : "N/A");

                    // releasedFlag
                    dto.setReleasedFlag(event.getCurrentlyPlaying() != null
                            ? event.getCurrentlyPlaying()
                            : false);

                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public EventDto updateEvent(Long id, EventDto eventDto, MultipartFile poster, List<MultipartFile> castImages) throws IOException {
    	Event event = eventRepository.findById(id).orElseThrow();

        // Poster update
        if (poster != null) {
            String base64Image = Base64.getEncoder().encodeToString(poster.getBytes());
            event.setImageurl(base64Image);
        }

        event.setName(eventDto.getName());
        event.setLanguage(eventDto.getLanguage());
        event.setGenre(eventDto.getGenre());
        event.setFormat(eventDto.getFormat());
        event.setDescription(eventDto.getDescription());
        event.setDuration(eventDto.getDuration());
        event.setReleaseDate(eventDto.getReleaseDate());
        event.setRating(eventDto.getRating());
        event.setLikes(eventDto.getLikes());
        event.setCurrentlyPlaying(eventDto.getCurrentlyPlaying());

        // Cast update
        if (castImages != null && eventDto.getCast() != null) {
            for (int i = 0; i < eventDto.getCast().size(); i++) {
                if (i < castImages.size()) {
                    String base64 = Base64.getEncoder().encodeToString(castImages.get(i).getBytes());
                    eventDto.getCast().get(i).setImg(base64);
                }
            }

            event.setCast(
            		eventDto.getCast().stream()
                        .map(c -> {
                            Cast cast = new Cast();
                            cast.setActorName(c.getActorName());
                            cast.setImg(c.getImg());
                            return cast;
                        })
                        .collect(Collectors.toList())
            );
        }

        return toDto(eventRepository.save(event));
    }


    @Override
    public void deleteEvent(Long id) {
    	Event event = eventRepository.findById(id).orElseThrow();
    	event.setDeleted(true);
    	eventRepository.save(event);
    }
    
    public List<EventDto> filterEvents(
            List<String> languages,
            List<String> genres,
            List<String> formats,
            String releaseMonth
    ) {
        Specification<Event> spec = MovieSpecification.filterEvents(languages, genres, formats, releaseMonth);
        return eventRepository.findAll(spec).stream()
                .filter(event -> !event.getDeleted())   
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public List<String> getAllLanguages(String contentType) {
        Set<String> languages = eventRepository.findByDeletedFalse()
                .stream()
                .filter(m -> contentType == null || contentType.isEmpty() ||
                contentType.equalsIgnoreCase(m.getContentType()))
                .filter(m -> m.getLanguage() != null)
                .flatMap(m -> m.getLanguage().stream())
                .collect(Collectors.toSet());
        List<String> sortedLanguages = new ArrayList<>(languages);
        Collections.sort(sortedLanguages);
        return sortedLanguages;
    }
 
    // Genres
    public List<String> getAllGenres(String contentType) {
        Set<String> genres = eventRepository.findByDeletedFalse()
                .stream()
                .filter(m -> contentType == null || contentType.isEmpty() ||
                contentType.equalsIgnoreCase(m.getContentType()))
                .filter(m -> m.getGenre() != null)
                .flatMap(m -> m.getGenre().stream())
                .collect(Collectors.toSet());
        List<String> sortedGenres = new ArrayList<>(genres);
        Collections.sort(sortedGenres);
        return sortedGenres;
    }
 
    // Formats
    public List<String> getAllFormats(String contentType) {
        Set<String> formats = eventRepository.findByDeletedFalse()
                .stream()
                .filter(m -> contentType == null || contentType.isEmpty() ||
                contentType.equalsIgnoreCase(m.getContentType()))
                .filter(m -> m.getFormat() != null)
                .flatMap(m -> m.getFormat().stream())
                .collect(Collectors.toSet());
        List<String> sortedFormats = new ArrayList<>(formats);
        Collections.sort(sortedFormats);
        return sortedFormats;
    }
 

}
