package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.ReserveSeatDTO;
import com.bookmyshow.main.dto.ShowRequestDTO;
import com.bookmyshow.main.model.*;
import com.bookmyshow.main.repository.*;
import com.bookmyshow.main.service.ShowService;


import com.bookmyshow.main.model.Event;   
import com.bookmyshow.main.model.Layout;  

import com.bookmyshow.main.repository.LayoutRepository;


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

  @Autowired
  private LayoutRepository layoutRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private ModelMapper modelMapper;

  private ShowRequestDTO convertEntityToDto(Show show) {
      return modelMapper.map(show, ShowRequestDTO.class);
  }


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

        show.setEventType(dto.getEventType());
        show.setCity(dto.getCity());
        show.setDate(dto.getDate());
        show.setStartTime(dto.getStartTime());
        show.setDuration(dto.getDuration());
        show.setStatus(dto.getStatus());
        


        if (dto.getLanguageName() != null) {
            List<Languages> languages = dto.getLanguageName().stream()
                .map(name -> languagesRepository.findByLanguageName(name)
                    .orElseThrow(() -> new RuntimeException("Language not found: " + name)))
                .collect(Collectors.toList());
            show.setLanguages(languages);
        }	

        if ("movies".equalsIgnoreCase(dto.getEventType())) {

            if (dto.getScreenName() == null || dto.getFormat() == null || dto.getLayoutName() == null || dto.getShowPrice() == null) {
                throw new RuntimeException("Missing required movie fields: screenName, format, layoutId, or showPrice");
            }

           Screen screen = screenRepository.findByVenueAndScreenName(venue, dto.getScreenName())
                    .orElseThrow(() -> new RuntimeException("Screen not found with name: " + dto.getScreenName()));
            show.setScreen(screen);
            show.setScreenName(dto.getScreenName());
            show.setFormat(dto.getFormat());
            
            System.out.println(" Screen ID Set: " + screen.getId());
            System.out.println(" ScreenName Set: " + show.getScreenName());
            System.out.println(" Format Set: " + show.getFormat());

           

            List<ShowCategory> showCategories = new ArrayList<>();
            for (Integer i = 0; i < dto.getLayoutName().size(); i++) {
            String layoutName = dto.getLayoutName().get(i);
                Integer price = dto.getShowPrice().get(i);

                Layout layout = layoutRepository.findByLayoutName(layoutName)
                    .orElseThrow(() -> new RuntimeException("Layout not found with name " + layoutName));

                ShowCategory showCategory = new ShowCategory();
                showCategory.setShow(show);
                showCategory.setLayout(layout);
                showCategory.setPrice(price);

                showCategories.add(showCategory);
            }
            show.setShowCategories(showCategories);

            if (dto.getReserveSeat() != null) {
                List<Seat> reservedSeats = new ArrayList<>();
                for (ReserveSeatDTO rsDto : dto.getReserveSeat()) {
                    UserMaster user = userRepository.findById(rsDto.getUserid())
                        .orElseThrow(() -> new RuntimeException("User not found with id " + rsDto.getUserid()));

                    for (String seatNum : rsDto.getUserReservationSeats()) {
                        Seat seat = new Seat();
                        seat.setUser(user);
                        seat.setSeatNumber(seatNum);
                        seat.setShow(show);
//                        seat.setScreen(screen);   

                        reservedSeats.add(seat);
                    }
                }
                show.setSeats(reservedSeats);
            }

        } else {
            
            if (dto.getShowPrice() == null || dto.getShowPrice().isEmpty()) {
                throw new RuntimeException("Price is required for non-movie shows");
            }


            show.setScreen(null);
            show.setFormat(null);

            if (dto.getReserveSeat() != null) {
                List<Seat> reservedSeats = new ArrayList<>();
                for (ReserveSeatDTO rsDto : dto.getReserveSeat()) {
                    UserMaster user = userRepository.findById(rsDto.getUserid())
                        .orElseThrow(() -> new RuntimeException("User not found with id " + rsDto.getUserid()));

                    for (String seatNum : rsDto.getUserReservationSeats()) {
                        Seat seat = new Seat();
                        seat.setUser(user);
                        seat.setSeatNumber(seatNum);
                        seat.setShow(show);
                        reservedSeats.add(seat);
                    }
                }
                show.setSeats(reservedSeats);
            }
        }

        Show savedShow = showRepository.save(show);

        return convertEntityToDto(savedShow);
    }

}
