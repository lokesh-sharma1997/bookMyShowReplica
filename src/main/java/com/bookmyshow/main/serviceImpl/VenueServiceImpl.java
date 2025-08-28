package com.bookmyshow.main.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.VenueDto;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.service.VenueService;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ModelMapper modelMapper;

    private VenueDto entityToDto(Venue entity) {
        return modelMapper.map(entity, VenueDto.class);
    }

    private Venue dtoToEntity(VenueDto dto) {
        return modelMapper.map(dto, Venue.class);
    }

    @Override
    public VenueDto createVenue(VenueDto dto) {
        Venue entity = dtoToEntity(dto);
        Venue saved = venueRepository.save(entity);
        return entityToDto(saved);
    }

    @Override
    public List<VenueDto> getAllVenues() {
        return venueRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VenueDto> getVenuesByName(String name) {
        return venueRepository.findBynameIgnoreCase(name)
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
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
