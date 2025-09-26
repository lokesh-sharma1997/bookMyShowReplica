package com.bookmyshow.main.serviceImpl;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.bookmyshow.main.dto.CastDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.dto.CategoryDTO;
import com.bookmyshow.main.dto.CrewDTO;
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
import com.bookmyshow.main.dto.ShowDTO;
import com.bookmyshow.main.dto.ShowTimeDTO;
import com.bookmyshow.main.dto.TagDTO;
import com.bookmyshow.main.events.NotificationEvent;
import com.bookmyshow.main.exception.EventCustomException;
import com.bookmyshow.main.model.Cast;
import com.bookmyshow.main.model.Categories;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.model.Crew;
import com.bookmyshow.main.model.DateFilter;

import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.model.Format;
import com.bookmyshow.main.model.Genres;
import com.bookmyshow.main.model.Languages;
import com.bookmyshow.main.model.Layout;
import com.bookmyshow.main.model.MoreFilters;
import com.bookmyshow.main.model.Price;
import com.bookmyshow.main.model.ReleaseMonth;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.ShowTime;
import com.bookmyshow.main.model.ShowTimeDate;
import com.bookmyshow.main.model.Tag;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.CastRepository;
import com.bookmyshow.main.repository.CategoriesRepository;
import com.bookmyshow.main.repository.CityRepository;
import com.bookmyshow.main.repository.CrewRepository;
import com.bookmyshow.main.repository.DateFilterRepository;
import com.bookmyshow.main.repository.EventRepository;
import com.bookmyshow.main.repository.FormatRepository;
import com.bookmyshow.main.repository.GenresRepository;
import com.bookmyshow.main.repository.LanguagesRepository;
import com.bookmyshow.main.repository.LayoutRepository;
import com.bookmyshow.main.repository.MoreFiltersRepository;
import com.bookmyshow.main.repository.PriceRepository;
import com.bookmyshow.main.repository.ReleaseMonthRepository;
import com.bookmyshow.main.repository.ScreenRepository;
import com.bookmyshow.main.repository.ShowRepository;
import com.bookmyshow.main.repository.ShowtimedateRepository;
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.service.EventService;
import java.util.Objects;

import com.bookmyshow.main.specification.EventSpecification;

@Service
public class EventServiceImpl implements EventService {

   @Autowired
   private VenueRepository venueRepository;

	@Autowired
	private EventRepository eventRepository;
	@Autowired
    private LanguagesRepository languagesRepository;
	@Autowired
    private GenresRepository genresRepository;   
	@Autowired
    private FormatRepository formatRepository;
	@Autowired
    private TagRepository tagRepository;
	@Autowired
    private ReleaseMonthRepository releaseMonthRepository;
	@Autowired
    private DateFilterRepository dateFilterRepository;
	@Autowired
    private CategoriesRepository categoriesRepository;
	@Autowired
    private MoreFiltersRepository moreFiltersRepository;
	@Autowired
    private PriceRepository priceRepository;
	@Autowired
    private CastRepository castRepository;
	@Autowired
    private CrewRepository crewRepository;
	@Autowired
    private CityRepository cityRepository;
	@Autowired
	private ScreenRepository screenRepository;
	@Autowired 
	 private LayoutRepository layoutRepository;
	@Autowired 
	 private ShowRepository showRepository;
	@Autowired 
	 private ShowtimedateRepository showtimedateRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

    

	private EventDTO toDto(Event event) {
	    EventDTO dto = new EventDTO();

	  
	    dto.setEventId(event.getEventId());
	    dto.setName(event.getName());
	    dto.setDescription(event.getDescription());
	    dto.setRunTime(event.getRunTime());
	    dto.setStartDate(event.getStartDate());
	    dto.setEndDate(event.getEndDate());
	    dto.setEventType(event.getEventType());
	    dto.setImageurl(event.getImageurl());
	    dto.setImdbRating(event.getImdbRating());
	    dto.setLikes(event.getLikes());
	    dto.setVotes(event.getVotes());
	    dto.setCurrentlyPlaying(event.getCurrentlyPlaying());
	   
	    dto.setAgeLimit(event.getAgeLimit());
	    dto.setReleasingOn(event.getReleasingOn());

	    if (event.getLanguages() != null)
	        dto.setLanguages(event.getLanguages()
	            .stream()
	            .map(lang -> lang.getLanguageId().intValue())
	            .toList());

	    if (event.getGenres() != null)
	        dto.setGenres(event.getGenres()
	            .stream()
	            .map(g -> g.getGenreId().intValue())
	            .toList());

	    if (event.getFormat() != null)
	        dto.setFormat(event.getFormat()
	            .stream()
	            .map(f -> f.getFormatId().intValue())
	            .toList());

	    if (event.getTag() != null)
	        dto.setTag(event.getTag()
	            .stream()
	            .map(t -> t.getTagId().intValue())
	            .toList());

	    if (event.getReleaseMonth() != null)
	        dto.setReleaseMonth(event.getReleaseMonth()
	            .stream()
	            .map(r -> r.getReleaseMonthId().intValue())
	            .toList());

	    if (event.getDateFilter() != null)
	        dto.setDateFilter(event.getDateFilter()
	            .stream()
	            .map(d -> d.getDateFilterId().intValue())
	            .toList());

	    if (event.getCategories() != null)
	        dto.setCategories(event.getCategories()
	            .stream()
	            .map(c -> c.getCategoryId().intValue())
	            .toList());

	    if (event.getMoreFilters() != null)
	        dto.setMoreFilters(event.getMoreFilters()
	            .stream()
	            .map(m -> m.getFilterId().intValue())
	            .toList());

	    if (event.getPrice() != null)
	        dto.setPrice(event.getPrice()
	            .stream()
	            .map(p -> p.getPriceId().intValue())
	            .toList());
	    if (event.getCast() != null) {
	        dto.setCast(event.getCast()
	            .stream()
	            .map(cast -> {
	                CastDTO castDto = new CastDTO();
	                castDto.setActorName(cast.getActorName());
	                castDto.setCastImg(cast.getCastImg());
	                return castDto;
	            })
	            .toList());
	    }
	    if (event.getCrew() != null) {
	        dto.setCrew(event.getCrew()
	            .stream()
	            .map(crew -> {
	                CrewDTO crewDto = new CrewDTO();
	                crewDto.setMemberName(crew.getMemberName());
	                crewDto.setCrewImg(crew.getCrewImg());
	                return crewDto;
	            })
	            .toList());
	    }


	    if (event.getCity() != null) {
	        dto.setCity(event.getCity()
	            .stream()
	            .map(city -> city.getCityId().intValue())
	            .toList());
	    }
	    if (event.getVenues() != null) {
	        dto.setVenue(event.getVenues()
	        .stream()
	        .map(venue->venue.getId().intValue())
	        .toList());
	    }

	    return dto;
	}



	private Event toEntity(EventDTO dto) {
		return mapper.map(dto, Event.class);
	}

	@Override
	public EventDTO createEvent(EventDTO eventDto, MultipartFile poster, List<MultipartFile> castImages,
			List<MultipartFile> crewImages)
	        throws IOException {

	   
	    String base64Poster = Base64.getEncoder().encodeToString(poster.getBytes());

	  
	    Event event = toEntity(eventDto);
	    event.setDeleted(false);
	    event.setImageurl(base64Poster);

	   
	    Set<String> predefined = new HashSet<>(Arrays.asList("Movie", "Show", "Cartoon", "Event"));
	    String eventType = eventDto.getEventType();
	    event.setEventType(predefined.contains(eventType) ? eventType : eventType);

	   
	    if (eventDto.getLanguages() != null) {
	        List<Languages> langs = languagesRepository.findAllById(eventDto.getLanguages());
	        event.setLanguages(langs);
	    }

	    if (eventDto.getGenres() != null) {
	        List<Genres> genres = genresRepository.findAllById(eventDto.getGenres());
	        event.setGenres(genres);
	    }

	    if (eventDto.getFormat() != null) {
	        List<Format> formats = formatRepository.findAllById(eventDto.getFormat());
	        event.setFormat(formats);
	    }

	    if (eventDto.getTag() != null) {
	        List<Tag> tags = tagRepository.findAllById(eventDto.getTag());
	        event.setTag(tags);
	    }

	    if (eventDto.getReleaseMonth() != null) {
	        List<ReleaseMonth> months = releaseMonthRepository.findAllById(eventDto.getReleaseMonth());
	        event.setReleaseMonth(months);
	    }

	    if (eventDto.getDateFilter() != null) {
	        List<DateFilter> filters = dateFilterRepository.findAllById(eventDto.getDateFilter());
	        event.setDateFilter(filters);
	    }

	    if (eventDto.getCategories() != null) {
	        List<Categories> cats = categoriesRepository.findAllById(eventDto.getCategories());
	        event.setCategories(cats);
	    }

	    if (eventDto.getMoreFilters() != null) {
	        List<MoreFilters> moreFilters = moreFiltersRepository.findAllById(eventDto.getMoreFilters());
	        event.setMoreFilters(moreFilters);
	    }

	    if (eventDto.getPrice() != null) {
	        List<Price> prices = priceRepository.findAllById(eventDto.getPrice());
	        event.setPrice(prices);
	    }



	    if (eventDto.getCast() != null) {

	        if (castImages != null) {
	            for (int i = 0; i < eventDto.getCast().size(); i++) {
	                if (i < castImages.size()) {
	                    String base64 = Base64.getEncoder().encodeToString(castImages.get(i).getBytes());
	                    eventDto.getCast().get(i).setCastImg(base64); 
	                }
	            }
	        }
	        List<Cast> castEntities = new ArrayList<>();
	        for (CastDTO c : eventDto.getCast()) {
	            Optional<Cast> existingCast = castRepository.findByActorName(c.getActorName());
	            Cast castEntity;
	            if (existingCast.isPresent()) {
	                castEntity = existingCast.get();
	                if (c.getCastImg() != null && !c.getCastImg().isEmpty()) {
	                    castEntity.setCastImg(c.getCastImg());
	                    castRepository.save(castEntity);
	                }
	            } else {
	                castEntity = new Cast();
	                castEntity.setActorName(c.getActorName());
	                castEntity.setCastImg(c.getCastImg());
	                castEntity = castRepository.save(castEntity);
	            }
	            castEntities.add(castEntity);
	        }
	        event.setCast(castEntities);

	    }
	    if (eventDto.getCrew() != null) {

	        if (crewImages != null) {
	            for (int i = 0; i < eventDto.getCrew().size(); i++) {
	                if (i < crewImages.size()) {
	                    String base64 = Base64.getEncoder().encodeToString(crewImages.get(i).getBytes());
	                    eventDto.getCrew().get(i).setCrewImg(base64); 
	                }
	            }
	        }

	        List<Crew> crewEntities = new ArrayList<>();
	        for (CrewDTO c : eventDto.getCrew()) {
	            Optional<Crew> existingCrew = crewRepository.findByMemberName(c.getMemberName());
	            Crew crewEntity;
	            if (existingCrew.isPresent()) {
	                crewEntity = existingCrew.get();
	                if (c.getCrewImg() != null && !c.getCrewImg().isEmpty()) {
	                    crewEntity.setCrewImg(c.getCrewImg());
	                    crewRepository.save(crewEntity);
	                }
	            } else {
	                crewEntity = new Crew();
	                crewEntity.setMemberName(c.getMemberName());
	                crewEntity.setCrewImg(c.getCrewImg());
	                crewEntity = crewRepository.save(crewEntity);
	            }
	            crewEntities.add(crewEntity);
	        }
	        event.setCrew(crewEntities);

	    }


	    if (eventDto.getCity() != null) {
	        List<City> cities = cityRepository.findAllById(eventDto.getCity());
	        event.setCity(cities);
	    }
	    if (eventDto.getVenue() != null) {
	     
	        List<Long> venueIds = eventDto.getVenue().stream()
	                                      .map(Integer::longValue) 
	                                      .collect(Collectors.toList());

	      
	        List<Venue> venues = venueRepository.findAllById(venueIds);

	     
	        event.setVenues(venues);
	    }

	    if (eventDto.getShow() != null) {
	        List<Show> shows = new ArrayList<>();
	        for (ShowDTO showDTO : eventDto.getShow()) {
	            Show show = new Show();
	            show.setId(showDTO.getShowid());

	            
	            if (showDTO.getVenue() != null) {
	                Venue venue = venueRepository.findById(showDTO.getVenue())
	                    .orElseThrow(() -> new RuntimeException("Venue not found"));
	                show.setVenue(venue);
	            }
	            if (showDTO.getScreen() != null) {
	                Screen screen = screenRepository.findById(showDTO.getScreen())
	                    .orElseThrow(() -> new RuntimeException("Screen not found"));
	                show.setScreen(screen);
	            }
	            if (showDTO.getLayout() != null) {
	                Layout layout = layoutRepository.findById(showDTO.getLayout())
	                    .orElseThrow(() -> new RuntimeException("Layout not found"));
	                show.setLayout(layout);
	            }

	         
	            show.setShowPrice(showDTO.getShowPrice());

	           
	            List<ShowTimeDate> showtimes = new ArrayList<>();
	            if (showDTO.getShowtimesdate() != null) {  
	                for (ShowTimeDTO showTimeDTO : showDTO.getShowtimesdate()) {

	                 
	                    ShowTimeDate showTimeDate = new ShowTimeDate();
	                    showTimeDate.setShowDate(showTimeDTO.getShowDate());
	                    showTimeDate.setShow(show); 
	                  
	                    List<ShowTime> showTimes = new ArrayList<>();

	                    
	                    if (showTimeDTO.getShowTime() != null) {
	                        for (LocalTime showTime : showTimeDTO.getShowTime()) {
	                            ShowTime showTimeEntity = new ShowTime();
	                            showTimeEntity.setShowTime(showTime);                 
	                            showTimeEntity.setShowTimeDate(showTimeDate);         
	                            showTimes.add(showTimeEntity);                         
	                        }
	                    }

	                    
	                    showTimeDate.setShowTimes(showTimes);

	                    
	                    showtimes.add(showTimeDate);
	                }
	            }

	            show.setEvent(event);  

	            show.setShowstimedate(showtimes);

	            shows.add(show);
	        }

	        event.setShows(shows); 
	    }else {
	        event.setShows(Collections.emptyList()); 
	    }

	 
	    Event savedEvent = eventRepository.save(event);
	    
	    eventPublisher.publishEvent(new NotificationEvent(
	            this,
	            "New "+savedEvent.getEventType()+" Added",
	            savedEvent.getName() + " is now available!",
	            savedEvent.getEventType()
	    ));

	   
	    for (Show show : event.getShows()) {
	        
	        for (ShowTimeDate showTimeDate : show.getShowstimedate()) {
	            
	        }
	        
	        showRepository.save(show);
	    }
	    return toDto(savedEvent);
	}


	@Override
	public EventResponseDto getEventById(Long id) {

		return eventRepository.findById(id)
				.filter(movie -> !movie.getDeleted() 
						)
				.map(this::toEventdto).orElseThrow(() -> new EventCustomException("Event not found with id: " + id));
	}

	

	

	

	

	public List<LanguagesDTO> getAllLanguages(String eventType) {
		
	    List<Event> events = eventRepository.findByEventType(eventType);
if (events == null || events.isEmpty())
{
	throw new EventCustomException("No Languages found");
}
	  
	    Set<Languages> allLanguagesSet = new HashSet<>();
	    for (Event event : events) {
	        if (event.getLanguages() != null) {
	            allLanguagesSet.addAll(event.getLanguages());
	        }
	    }

	  
	    return allLanguagesSet.stream()
	            .map(lang -> mapper.map(lang, LanguagesDTO.class))
	            .collect(Collectors.toList());
	}



	@Override
	public List<GenresDTO> getAllGenres(String eventType) {
		 List<Event> events = eventRepository.findByEventType(eventType);

		 if (events == null || events.isEmpty())
		 {
		 	throw new EventCustomException("No Genres found");
		 }
		    Set<Genres> allGenresSet = new HashSet<>();
		    for (Event event : events) {
		        if (event.getGenres() != null) {
		        	allGenresSet.addAll(event.getGenres());
		        }
		    }

		  
		    return allGenresSet.stream()
		            .map(genre -> mapper.map(genre, GenresDTO.class))
		            .collect(Collectors.toList());
	}

	@Override
	public List<FormatDTO> getAllFormats() {
	    List<Format> allFormats = formatRepository.findAll();
	    return allFormats.stream()
	                     .map(format -> mapper.map(format, FormatDTO.class))
	                     .collect(Collectors.toList());
	}


	@Override
	public List<TagDTO> getAllTags() {
	    List<Tag> allTags = tagRepository.findAll();
	    return allTags.stream()
	                  .map(tag -> mapper.map(tag, TagDTO.class))
	                  .collect(Collectors.toList());
	}
	@Override
	public List<ReleaseMonthDTO> getAllReleaseMonths() {
	    List<ReleaseMonth> releaseMonths = releaseMonthRepository.findAll();
	    return releaseMonths.stream()
	                        .map(month -> mapper.map(month, ReleaseMonthDTO.class))
	                        .collect(Collectors.toList());
	}
	
	@Override
	public List<DateFilterDTO> getAllDateFilters() {
	    List<DateFilter> dateFilters = dateFilterRepository.findAll();
	    return dateFilters.stream()
	                      .map(filter -> mapper.map(filter, DateFilterDTO.class))
	                      .collect(Collectors.toList());
	}

	@Override
	public List<CategoryDTO> getAllCategories(String eventType) {
	    List<Event> events = eventRepository.findByEventType(eventType);
	    if (events == null || events.isEmpty())
		 {
		 	throw new EventCustomException("No Categories found");
		 }
	    Set<Categories> allCategorySet = new HashSet<>();
	    for (Event event : events) {
	        if (event.getCategories() != null) {
	            allCategorySet.addAll(event.getCategories());
	        }
	    }

	  
	    mapper.typeMap(Categories.class, CategoryDTO.class).addMappings(mapper -> {
	        mapper.map(Categories::getCategoryId, CategoryDTO::setCategoryId);
	        mapper.map(Categories::getCategoriesName, CategoryDTO::setCategoryName);
	    });

	    return allCategorySet.stream()
	            .map(category -> mapper.map(category, CategoryDTO.class))
	            .collect(Collectors.toList());
	}

	
	@Override
	public List<MoreFilterDTO> getAllMoreFilters(String eventType) {
	    List<Event> events = eventRepository.findByEventType(eventType);
	    if (events == null || events.isEmpty())
		 {
		 	throw new EventCustomException("No MoreFilters found");
		 }
	    Set<MoreFilters> allMoreFiltersSet = new HashSet<>();
	    for (Event event : events) {
	        if (event.getMoreFilters() != null) {
	            allMoreFiltersSet.addAll(event.getMoreFilters());
	        }
	    }

	   
	    mapper.typeMap(MoreFilters.class, MoreFilterDTO.class).addMappings(m -> {
	        m.map(MoreFilters::getFilterId, MoreFilterDTO::setMoreFilterId);
	        m.map(MoreFilters::getName, MoreFilterDTO::setMoreFilterName);
	    });

	    return allMoreFiltersSet.stream()
	            .map(mfilter -> mapper.map(mfilter, MoreFilterDTO.class))
	            .collect(Collectors.toList());
	}

	

	@Override
	public List<PriceDTO> getAllPrices() {
	    List<Price> prices = priceRepository.findAll();
	    return prices.stream()
	                 .map(price -> mapper.map(price, PriceDTO.class))
	                 .collect(Collectors.toList());
	}


	
	@Override
	public EventDTO updateEvent(Long id, EventDTO eventDto, 
	        MultipartFile poster, List<MultipartFile> castImages,
	        List<MultipartFile> crewImages)
	        throws IOException {
	    Event event = eventRepository.findById(id)
	            .orElseThrow(() -> new EventCustomException("Event not found with id: " + id));
	    if(event.getDeleted())
	    {
	    	throw new EventCustomException("Event not found with id: " + id);
	    }
	    event.setDeleted(false);

	    if (poster != null && !poster.isEmpty()) {
	        String base64Image = Base64.getEncoder().encodeToString(poster.getBytes());
	        event.setImageurl(base64Image);
	    }

	 
	    if (eventDto.getName() != null ) event.setName(eventDto.getName());
	    if (eventDto.getDescription() != null) event.setDescription(eventDto.getDescription());
	    if (eventDto.getRunTime() != null) event.setRunTime(eventDto.getRunTime());
	    if (eventDto.getStartDate() != null) event.setStartDate(eventDto.getStartDate());
	    if (eventDto.getEndDate() != null) event.setEndDate(eventDto.getEndDate());
	    if (eventDto.getEventType() != null) event.setEventType(eventDto.getEventType());
	    if (eventDto.getImdbRating() != null) event.setImdbRating(eventDto.getImdbRating());
	    if (eventDto.getLikes() != null) event.setLikes(eventDto.getLikes());
	    if (eventDto.getVotes() != null) event.setVotes(eventDto.getVotes());
	    if (eventDto.getCurrentlyPlaying() != null) event.setCurrentlyPlaying(eventDto.getCurrentlyPlaying());
	    if (eventDto.getAgeLimit() != 0) event.setAgeLimit(eventDto.getAgeLimit());  
	    if (eventDto.getReleasingOn() != null) event.setReleasingOn(eventDto.getReleasingOn());

	  
	    if (eventDto.getLanguages() != null) {
	        event.setLanguages(languagesRepository.findAllById(eventDto.getLanguages()));
	    }

	    if (eventDto.getGenres() != null) {
	        event.setGenres(genresRepository.findAllById(eventDto.getGenres()));
	    }

	    if (eventDto.getFormat() != null) {
	        event.setFormat(formatRepository.findAllById(eventDto.getFormat()));
	    }

	    if (eventDto.getTag() != null) {
	        event.setTag(tagRepository.findAllById(eventDto.getTag()));
	    }

	    if (eventDto.getReleaseMonth() != null) {
	        event.setReleaseMonth(releaseMonthRepository.findAllById(eventDto.getReleaseMonth()));
	    }

	    if (eventDto.getDateFilter() != null) {
	        event.setDateFilter(dateFilterRepository.findAllById(eventDto.getDateFilter()));
	    }

	    if (eventDto.getCategories() != null) {
	        event.setCategories(categoriesRepository.findAllById(eventDto.getCategories()));
	    }

	    if (eventDto.getMoreFilters() != null) {
	        event.setMoreFilters(moreFiltersRepository.findAllById(eventDto.getMoreFilters()));
	    }

	    if (eventDto.getPrice() != null) {
	        event.setPrice(priceRepository.findAllById(eventDto.getPrice()));
	    }
	    if (eventDto.getVenue() != null) {
	      
	        List<Long> venueIds = eventDto.getVenue().stream()
	                                      .map(Integer::longValue) 
	                                      .collect(Collectors.toList());

	       
	        event.setVenues(venueRepository.findAllById(venueIds));
	    }


	   
	    if (eventDto.getCast() != null) {
	        if (castImages != null) {
	            for (int i = 0; i < eventDto.getCast().size(); i++) {
	                if (i < castImages.size()) {
	                    String base64 = Base64.getEncoder().encodeToString(castImages.get(i).getBytes());
	                    eventDto.getCast().get(i).setCastImg(base64); 
	                }
	            }
	        }

	        List<Cast> castEntities = eventDto.getCast().stream()
	            .map(c -> {
	                Optional<Cast> existingCast = castRepository.findByActorName(c.getActorName());
	                Cast cast = existingCast.orElseGet(() -> {
	                    Cast newCast = new Cast();
	                    newCast.setActorName(c.getActorName());
	                    newCast.setCastImg(c.getCastImg());
	                    return castRepository.save(newCast);
	                });

	              
	                if (c.getCastImg() != null && !c.getCastImg().isEmpty()) {
	                    cast.setCastImg(c.getCastImg());
	                    castRepository.save(cast);
	                }

	                return cast;
	            })
	            .collect(Collectors.toList());

	        event.setCast(castEntities);
	    }

	  
	    if (eventDto.getCrew() != null) {
	        if (crewImages != null) {
	            for (int i = 0; i < eventDto.getCrew().size(); i++) {
	                if (i < crewImages.size()) {
	                    String base64 = Base64.getEncoder().encodeToString(crewImages.get(i).getBytes());
	                    eventDto.getCrew().get(i).setCrewImg(base64); 
	                }
	            }
	        }

	        List<Crew> crewEntities = eventDto.getCrew().stream()
	            .map(c -> {
	                Optional<Crew> existingCrew = crewRepository.findByMemberName(c.getMemberName());
	                Crew crew = existingCrew.orElseGet(() -> {
	                    Crew newCrew = new Crew();
	                    newCrew.setMemberName(c.getMemberName());
	                    newCrew.setCrewImg(c.getCrewImg());
	                    return crewRepository.save(newCrew);
	                });

	                if (c.getCrewImg() != null && !c.getCrewImg().isEmpty()) {
	                    crew.setCrewImg(c.getCrewImg());
	                    crewRepository.save(crew);
	                }

	                return crew;
	            })
	            .collect(Collectors.toList());

	        event.setCrew(crewEntities);
	    }
	    if (eventDto.getCity() != null) {
	        event.setCity(cityRepository.findAllById(eventDto.getCity()));
	    }
	    
	    
	    if (eventDto.getShow() != null) {
	        List<Show> shows = new ArrayList<>();
	        for (ShowDTO showDTO : eventDto.getShow()) {
	           
	            Show show = (showDTO.getShowid() != null) ?
	                          showRepository.findById(showDTO.getShowid()).orElse(new Show()) :
	                          new Show();

	           
	            if (showDTO.getVenue() != null) {
	                venueRepository.findById(showDTO.getVenue())
	                    .ifPresent(show::setVenue);
	            }
	            if (showDTO.getScreen() != null) {
	                screenRepository.findById(showDTO.getScreen())
	                    .ifPresent(show::setScreen);
	            }
	            if (showDTO.getLayout() != null) {
	                layoutRepository.findById(showDTO.getLayout())
	                    .ifPresent(show::setLayout);
	            }

	            if (showDTO.getShowPrice() != null) {
	                show.setShowPrice(showDTO.getShowPrice());
	            }
	            show.setEvent(event);

	            List<ShowTimeDate> showtimes = new ArrayList<>();
	            if (showDTO.getShowtimesdate() != null) {
	                for (ShowTimeDTO std : showDTO.getShowtimesdate()) {
	                    if (std == null || std.getShowDate() == null) continue; 
	                    ShowTimeDate stdEntity = new ShowTimeDate();
	                    stdEntity.setShowDate(std.getShowDate());
	                    stdEntity.setShow(show);

	                    List<ShowTime> times = new ArrayList<>();
	                    if (std.getShowTime() != null) {
	                        for (LocalTime t : std.getShowTime()) {
	                            if (t != null) {
	                                ShowTime st = new ShowTime();
	                                st.setShowTime(t);
	                                st.setShowTimeDate(stdEntity);
	                                times.add(st);
	                            }
	                        }
	                    }

	                    if (!times.isEmpty()) {
	                        stdEntity.setShowTimes(times);
	                        showtimes.add(stdEntity);
	                    }
	                }
	            }

	            show.setShowstimedate(showtimes);
	            shows.add(showRepository.save(show));
	        }
	        event.setShows(shows);
	    } else {
	        event.setShows(Collections.emptyList());
	    }

	    Event updated = eventRepository.save(event);
	    return toDto(updated);
	}

	
	
	
	

	
	public List<EventSearchDTO> searchEventNames(String name, List<String> eventTypes) {
	    List<Event> events;
	 
	    if (eventTypes != null && !eventTypes.isEmpty()) {
	        List<String> lowerEventTypes = eventTypes.stream()
	                                                 .map(String::toLowerCase)
	                                                 .toList();
	 
	        events = eventRepository.searchByNameAndEventTypes(name, lowerEventTypes);
	    } else {
	        events = eventRepository.searchByNameOnly(name);
	        
	    }
	 
	    return events.stream()
	                 .map(event -> new EventSearchDTO(event.getEventId(), event.getName()))
	                 .toList();
	}




	@Override
	public boolean deleteEvent(Long id) {
	    Event event = eventRepository.findById(id)
	            .orElseThrow(() -> new EventCustomException("Event not found with this id: " + id));

	    event.setDeleted(true);
	    eventRepository.save(event);

	    return true; // Successfully marked as deleted
	}




//	public Page<EventResponseDtoCard> filterEvents(
//	        String type,
//	        List<Integer> languages,
//	        List<Integer> genres,
//	        List<Integer> formats,
//	        List<Integer> tags,
//	        List<Integer> categories,
//	        List<Integer> price,
//	        List<Integer> moreFilters,
//	        List<Integer> releaseMonths,
//	        List<Integer> dateFilters,
//	        int page, 
//	        int size  
//	) {
//	    
//	    Pageable pageable = PageRequest.of(page, size);
//	   
//	  
//	    Specification<Event> spec = EventSpecification.filterEvents(
//	            type, languages, genres, formats, tags, categories, price, moreFilters, releaseMonths, dateFilters
//	    );
//
//	 
//	    Page<Event> eventPage = eventRepository.findAll(spec, pageable);
//
//	 
//	    List<EventResponseDtoCard> eventDtoList = eventPage.getContent().stream()
//	            .filter(event -> !event.getDeleted()) 
//	            .map(this::mapToResponseDto) 
//	            .collect(Collectors.toList());
//
//	   
//	    return new PageImpl<>(eventDtoList, pageable, eventPage.getTotalElements());
//	}

	
	
	public Page<EventResponseDtoCard> filterEvents(
	        String type,
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
	        boolean includeCurrentlyPlaying
	) {
	    Pageable pageable = PageRequest.of(page, size);

	    Specification<Event> spec = EventSpecification.filterEvents(
	        type, languages, genres, formats, tags, categories, price, moreFilters, releaseMonths, dateFilters
	    );

	    if ("Movie".equalsIgnoreCase(type) && !includeCurrentlyPlaying) {
	        // Add condition to specification that currentlyPlaying must be true
	        Specification<Event> currentlyPlayingSpec = (root, query, criteriaBuilder) ->
	            criteriaBuilder.isFalse(root.get("currentlyPlaying"));
	        spec = spec.and(currentlyPlayingSpec);
	    }

	    Page<Event> eventPage = eventRepository.findAll(spec, pageable);
System.out.print(eventPage);
	    List<EventResponseDtoCard> eventDtoList = eventPage.getContent().stream()
	            .filter(event -> !event.getDeleted())
	            .map(this::mapToResponseDto)
	            .collect(Collectors.toList());

	    return new PageImpl<>(eventDtoList, pageable, eventPage.getTotalElements());
	}

	
	
	
	
	@Override
	public List<EventResponseDtoCard> getPopularEvents(String eventType) {
	    List<Event> events;

	    if (eventType != null && !eventType.isEmpty()) {
	        events = eventRepository.findTop10ByEventTypeOrderByReleasingOnDesc(eventType);
	    } else {
	        events = eventRepository.findTop10ByOrderByReleasingOnDesc();
	    }
	    if (events == null || events.isEmpty())
		 {
		 	throw new EventCustomException("No Events found");
		 }
	    return events.stream()
	            .filter(event -> !event.getDeleted())
	            .map(this::mapToResponseDto)
	            .collect(Collectors.toList());
	}



	private EventResponseDtoCard mapToResponseDto(Event event) {
		EventResponseDtoCard dto = new EventResponseDtoCard();

	    dto.setEventId(event.getEventId());
	    dto.setName(event.getName());
	    dto.setLikes(event.getLikes() != null ? event.getLikes() : 0.0);
	    dto.setImageurl(event.getImageurl());
	    dto.setReleasingOn(event.getReleasingOn());
	    dto.setStartDate(event.getStartDate());
	    dto.setAgeLimit(event.getAgeLimit());
	    
	    
	    if (event.getShows() != null && !event.getShows().isEmpty()) {
	        List<Integer> showPrices = event.getShows()
	                                        .stream()
	                                        .map(Show::getShowPrice)
	                                        .toList();
	        dto.setPricelist(showPrices); 
	    }

	    
	    if (event.getShows() != null && !event.getShows().isEmpty()) {
	        LocalTime firstShowTime = event.getShows().stream()
	                                       .filter(s -> s.getShowstimedate() != null && !s.getShowstimedate().isEmpty())
	                                       .flatMap(s -> s.getShowstimedate().stream())
	                                       .filter(std -> std.getShowTimes() != null && !std.getShowTimes().isEmpty())
	                                       .flatMap(std -> std.getShowTimes().stream())
	                                       .map(ShowTime::getShowTime)
	                                       .findFirst()
	                                       .orElse(null);
	        dto.setStarttime(firstShowTime);
	    }
	   
	    if (event.getVenues() != null && !event.getVenues().isEmpty()) {
		       
	        List<String> venueName = event.getVenues()
	                                   .stream()
	                                   .map(v -> v.getVenueName())
	                                   .toList();
	        dto.setVenueName(venueName);
	    } else {
	        dto.setVenueName(List.of()); 
	    }
	    if (event.getGenres() != null && !event.getGenres().isEmpty()) {
	       
	        List<String> genre = event.getGenres()
	                                   .stream()
	                                   .map(g -> g.getGenresName())
	                                   .toList();
	        dto.setGenres(genre);
	    } else {
	        dto.setGenres(List.of()); 
	    }
	    if (event.getLanguages() != null && !event.getLanguages().isEmpty()) {
	        List<String> languageList = event.getLanguages()
	                                         .stream()
	                                         .map(Languages::getLanguageName)
	                                         .toList();
	        dto.setLanguages(languageList);
	    } else {
	        dto.setLanguages(List.of());
	    }

	    // Categories
	    if (event.getCategories() != null && !event.getCategories().isEmpty()) {
	        List<String> categoryList = event.getCategories()
	                                         .stream()
	                                         .map(Categories::getCategoriesName)
	                                         .toList();
	        dto.setCategories(categoryList);
	    } else {
	        dto.setCategories(List.of());
	    }

	    dto.setVotes(event.getVotes() != null ? event.getVotes() : 0.0);
	    dto.setImdbRating(event.getImdbRating() != null ? event.getImdbRating() : 0.0);
	    dto.setReleasedFlag(event.getCurrentlyPlaying() != null ? event.getCurrentlyPlaying() : false);

	    return dto;
	}

	public  EventResponseDto toEventdto(Event event) {
		EventResponseDto dto = new EventResponseDto();
	    
	    dto.setEventId(event.getEventId());
	    dto.setName(event.getName());
	    dto.setDescription(event.getDescription());
	    dto.setRunTime(event.getRunTime());
	    dto.setStartDate(event.getStartDate());
	    dto.setEndDate(event.getEndDate());
	    dto.setEventType(event.getEventType());
	    dto.setImageurl(event.getImageurl());
	    dto.setImdbRating(event.getImdbRating());
	    dto.setLikes(event.getLikes());
	    dto.setVotes(event.getVotes());
	    dto.setCurrentlyPlaying(event.getCurrentlyPlaying());
	    dto.setDeleted(event.getDeleted());
	    dto.setAgeLimit(event.getAgeLimit() != null ? event.getAgeLimit() : 0);
	    dto.setReleasingOn(event.getReleasingOn());
	    if (event.getVenues() != null && !event.getVenues().isEmpty()) {
		       
	        List<String> venueName = event.getVenues()
	                                   .stream()
	                                   .map(v -> v.getVenueName())
	                                   .toList();
	        dto.setVenueName(venueName);
	    } else {
	        dto.setVenueName(List.of()); 
	    }
	    
	    
	    dto.setLanguages(event.getLanguages() != null ? event.getLanguages().stream()
	        .map(Languages::getLanguageName)
	        .toList() : new ArrayList<>());

	    dto.setGenres(event.getGenres() != null ? event.getGenres().stream()
	        .map(Genres::getGenresName)
	        .toList() : new ArrayList<>());

	    dto.setFormat(event.getFormat() != null ? event.getFormat().stream()
	        .map(Format::getFormatName)
	        .toList() : new ArrayList<>());

	    dto.setTag(event.getTag() != null ? event.getTag().stream()
	        .map(Tag::getTagName)
	        .toList() : new ArrayList<>());

	    dto.setReleaseMonth(event.getReleaseMonth() != null ? event.getReleaseMonth().stream()
	        .map(ReleaseMonth::getReleaseMonthName)
	        .toList() : new ArrayList<>());

	    dto.setDateFilter(event.getDateFilter() != null ? event.getDateFilter().stream()
	        .map(DateFilter::getDateFilterName)
	        .toList() : new ArrayList<>());

	    dto.setCategories(event.getCategories() != null ? event.getCategories().stream()
	        .map(Categories::getCategoriesName)
	        .toList() : new ArrayList<>());

	    dto.setMoreFilters(event.getMoreFilters() != null ? event.getMoreFilters().stream()
	        .map(MoreFilters::getName)
	        .toList() : new ArrayList<>());

	    dto.setPrice(event.getPrice() != null ? event.getPrice().stream()
	        .map(Price::getPriceRange)
	        .toList() : new ArrayList<>());

	    if (event.getCast() != null) {
	        dto.setCast(event.getCast()
	            .stream()
	            .map(cast -> {
	                CastDTO castDto = new CastDTO();
	                castDto.setActorName(cast.getActorName());
	                castDto.setCastImg(cast.getCastImg());
	                return castDto;
	            })
	            .toList());
	    }
	    if (event.getCrew() != null) {
	        dto.setCrew(event.getCrew()
	            .stream()
	            .map(crew -> {
	                CrewDTO crewDto = new CrewDTO();
	                crewDto.setMemberName(crew.getMemberName());
	                crewDto.setCrewImg(crew.getCrewImg());
	                return crewDto;
	            })
	            .toList());
	    }

	    dto.setCity(event.getCity() != null ? event.getCity().stream()
	        .map(City::getName)
	        .toList() : new ArrayList<>());
	    
	    
	 // Price extract
	    if (event.getShows() != null && !event.getShows().isEmpty()) {
	        List<Integer> showPrices = event.getShows()
	                                        .stream()
	                                        .map(Show::getShowPrice)
	                                        .toList();
	        dto.setPricelist(showPrices); 
	    }

	    
	    if (event.getShows() != null && !event.getShows().isEmpty()) {
	        LocalTime firstShowTime = event.getShows().stream()
	                                       .filter(s -> s.getShowstimedate() != null && !s.getShowstimedate().isEmpty())
	                                       .flatMap(s -> s.getShowstimedate().stream())
	                                       .filter(std -> std.getShowTimes() != null && !std.getShowTimes().isEmpty())
	                                       .flatMap(std -> std.getShowTimes().stream())
	                                       .map(ShowTime::getShowTime)
	                                       .findFirst()
	                                       .orElse(null);
	        dto.setStarttime(firstShowTime);
	    }

	    return dto;
	}






}