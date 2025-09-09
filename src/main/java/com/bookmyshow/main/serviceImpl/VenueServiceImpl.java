package com.bookmyshow.main.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bookmyshow.main.dto.AddressDTO;
import com.bookmyshow.main.dto.ScreenDTO;
import com.bookmyshow.main.dto.SupportedCategoryDTO;
import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.model.Layout;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.LayoutRepository;
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.service.VenueService;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LayoutRepository layoutRepository;

    private VenueDTO entityToDto(Venue entity) {
        return modelMapper.map(entity, VenueDTO.class);
    }

    private Venue dtoToEntity(VenueDTO dto) {
        return modelMapper.map(dto, Venue.class);
    }
    @Override
    public VenueDTO createVenue(VenueDTO dto) {
        // Convert DTO to entity
        Venue entity = dtoToEntity(dto);

        // Check if the venue is for movies
        if ("movies".equalsIgnoreCase(entity.getVenueFor())) {
            // Handle screens and layouts for movie venues

            // Initialize the screens list if it is null
            if (entity.getScreens() == null) {
                entity.setScreens(new ArrayList<>());
            }

            // Map screens from DTO to entity
            for (Screen screen : entity.getScreens()) {
                screen.setVenue(entity); // Associate each screen with the venue

                // Check if layouts are provided for the screen and map them
                if (screen.getLayouts() != null) {
                    for (Layout layout : screen.getLayouts()) {
                        layout.setScreen(screen); // Associate layout with the screen
                    }
                }
            }
        } else {
            // For non-movie venues (events/sports), clear the screens list
            entity.setScreens(new ArrayList<>());
        }

        // Save the venue entity to the repository
        Venue saved = venueRepository.save(entity);

        // Convert the saved entity back to DTO
        return entityToDto(saved);
    }


    @Override
    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll()
                .stream()
                .filter(data -> !Boolean.TRUE.equals(data.getDeleted()))
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<VenueDTO> getVenuesByCity(String city) {
        // Fetch venues where the address city matches the provided city
        List<Venue> venues = venueRepository.findByAddressCity(city);

        // Map the Venue entities to VenueDTOs
        return venues.stream().map(venue -> {
            VenueDTO dto = new VenueDTO();
            
            // Map basic fields
            dto.setId(venue.getId());
            dto.setVenueName(venue.getVenueName());
            dto.setVenueCapacity(venue.getVenueCapacity());
            dto.setVenueFor(venue.getVenueFor());
            dto.setVenueType(venue.getVenueType());

            // Handling supported categories with null check
            List<SupportedCategoryDTO> supportedCategoryDTOs = venue.getSupportedCategories() != null 
                ? venue.getSupportedCategories().stream().map(category -> {
                    SupportedCategoryDTO categoryDTO = new SupportedCategoryDTO();
                    categoryDTO.setId(category.getId()); // Uses getId() from SupportedCategory
                    categoryDTO.setCategoryName(category.getCategoryname()); // Uses getCategoryname() from SupportedCategory
                    return categoryDTO;
                }).collect(Collectors.toList()) 
                : Collections.emptyList();
            dto.setSupportedCategories(supportedCategoryDTOs);

            // Handle venue address
            if (venue.getAddress() != null) {
                AddressDTO addressDto = new AddressDTO();
                addressDto.setId(venue.getAddress().getId());
                addressDto.setStreet(venue.getAddress().getStreet());
                addressDto.setCity(venue.getAddress().getCity());
                addressDto.setPin(venue.getAddress().getPin());
                dto.setAddress(addressDto);
            }

            // Handle screens (only for movie venues)
            if ("movies".equalsIgnoreCase(venue.getVenueFor()) && venue.getScreens() != null) {
                List<ScreenDTO> screenDTOs = venue.getScreens().stream().map(screen -> {
                    ScreenDTO screenDto = new ScreenDTO();
                    screenDto.setId(screen.getId());
                    screenDto.setScreenName(screen.getScreenName());
                    // You can add additional screen details here if needed
                    return screenDto;
                }).collect(Collectors.toList());
                dto.setScreens(screenDTOs);  // Setting screens for movie venues
            }

            return dto;
        }).collect(Collectors.toList());
    }




    @Override
    public boolean softDeleteVenue(Long id) {
        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            venue.setDeleted(true);
            venueRepository.save(venue);
            return true;
        }
        return false;
    }
}
