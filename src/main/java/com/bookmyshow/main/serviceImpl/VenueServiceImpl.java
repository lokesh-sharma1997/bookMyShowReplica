package com.bookmyshow.main.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.AddressDTO;
import com.bookmyshow.main.dto.VenueDTO;
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

        return venues.stream().map(venue -> {
            VenueDTO dto = new VenueDTO();

            dto.setId(venue.getId());
            dto.setVenueName(venue.getVenueName());
            dto.setVenueCapacity(venue.getVenueCapacity());
            dto.setVenueFor(venue.getVenueFor());
            dto.setVenueType(venue.getVenueType());
            dto.setSupportedCategories(
                venue.getSupportedCategories() != null ? venue.getSupportedCategories() : Collections.emptySet()
            );
            dto.setAdditionalFields(venue.getAdditionalFields());
            dto.setDeleted(venue.getDeleted());

            if (venue.getAddress() != null) {
                AddressDTO addressDto = new AddressDTO(
                    venue.getAddress().getStreet(),
                    venue.getAddress().getCity(),
                    venue.getAddress().getPin()
                );
                dto.setAddress(addressDto);
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
