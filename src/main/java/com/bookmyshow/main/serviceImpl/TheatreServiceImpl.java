package com.bookmyshow.main.serviceImpl;



import com.bookmyshow.main.dto.TheatreDto;
import com.bookmyshow.main.model.Theatre;
import com.bookmyshow.main.repository.TheatreRepository;
import com.bookmyshow.main.service.TheatreService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory. annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TheatreServiceImpl implements TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ModelMapper modelMapper;

   private TheatreDto entityToDto(Theatre entity) {
        return modelMapper.map(entity, TheatreDto.class);
    }

   private Theatre dtoToEntity(TheatreDto dto) {
        return modelMapper.map(dto, Theatre.class);
    }
    public TheatreDto createTheatre(TheatreDto dto) {
        Theatre entity = dtoToEntity(dto);
        Theatre saved = theatreRepository.save(entity);
        return entityToDto(saved);
    }
   public List<TheatreDto> getAllTheatres() {
        return theatreRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

   public List<TheatreDto> getTheatresByName(String name) {
        return theatreRepository.findBynameIgnoreCase(name)
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

   public boolean softDeleteTheatre(Long id) {
	    Optional<Theatre> optionalTheatre = theatreRepository.findById(id);
	    if (optionalTheatre.isPresent()) {
	        Theatre theatre = optionalTheatre.get();
	        theatre.setDeleted(true);
	        theatreRepository.save(theatre);
	        return true;
	    }
	    return false;
	}
}
