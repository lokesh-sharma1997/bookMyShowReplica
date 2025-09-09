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
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.service.VenueService;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ModelMapper modelMapper;

    private VenueDTO entityToDto(Venue entity) {
        return modelMapper.map(entity, VenueDTO.class);
    }

    private Venue dtoToEntity(VenueDTO dto) {
        return modelMapper.map(dto, Venue.class);
    }

    @Override
    public VenueDTO createVenue(VenueDTO dto) {
        Venue entity = dtoToEntity(dto);

        if ("movies".equalsIgnoreCase(entity.getVenueFor())) {
            if (entity.getScreens() == null) {
                entity.setScreens(new ArrayList<>());
            }

            for (Screen screen : entity.getScreens()) {
                screen.setVenue(entity);

                if (screen.getLayouts() != null) {
                    for (Layout layout : screen.getLayouts()) {  
                        layout.setScreen(screen);
                    }
                }
            }
        } else {
            entity.setScreens(new ArrayList<>());
        }

        Venue saved = venueRepository.save(entity);
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
        List<Venue> venues = venueRepository.findByAddressCity(city);

        if (venues == null || venues.isEmpty()) {
            return Collections.emptyList();
        }

        return venues.stream().map(venue -> {
            VenueDTO dto = new VenueDTO();
            dto.setId(venue.getId());
            dto.setVenueName(venue.getVenueName());
            dto.setVenueCapacity(venue.getVenueCapacity());
            dto.setVenueFor(venue.getVenueFor());
            dto.setVenueType(venue.getVenueType());

            List<SupportedCategoryDTO> supportedCategoryDTOs = venue.getSupportedCategories() != null 
                ? venue.getSupportedCategories().stream().map(category -> {
                    SupportedCategoryDTO categoryDTO = new SupportedCategoryDTO();
                    categoryDTO.setId(category.getId());
                    categoryDTO.setCategoryName(category.getCategoryname());
                    return categoryDTO;
                }).collect(Collectors.toList()) 
                : Collections.emptyList();
            dto.setSupportedCategories(supportedCategoryDTOs);

            if (venue.getAddress() != null) {
                AddressDTO addressDto = new AddressDTO();
                addressDto.setId(venue.getAddress().getId());
                addressDto.setStreet(venue.getAddress().getStreet());
                addressDto.setCity(venue.getAddress().getCity());
                addressDto.setPin(venue.getAddress().getPin());
                dto.setAddress(addressDto);
            }

            if ("movies".equalsIgnoreCase(venue.getVenueFor()) && venue.getScreens() != null) {
                List<ScreenDTO> screenDTOs = venue.getScreens().stream().map(screen -> {
                    ScreenDTO screenDto = new ScreenDTO();
                    screenDto.setId(screen.getId());
                    screenDto.setScreenName(screen.getScreenName());
                    return screenDto;
                }).collect(Collectors.toList());
                dto.setScreens(screenDTOs);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean softDeleteVenue(Long id) {
        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            if (Boolean.TRUE.equals(venue.getDeleted())) {
                return false;
            }
            venue.setDeleted(true);
            venueRepository.save(venue);
            return true;
        }
        return false;
    }
}
