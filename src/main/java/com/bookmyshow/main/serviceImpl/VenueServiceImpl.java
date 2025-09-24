package com.bookmyshow.main.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.AddressDTO;
import com.bookmyshow.main.dto.LayoutDTO;
import com.bookmyshow.main.dto.ScreenDTO;
import com.bookmyshow.main.dto.TimeSlotDTO;
import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.exception.VenueNotFoundException;
import com.bookmyshow.main.model.Address;
import com.bookmyshow.main.model.Amenity;
import com.bookmyshow.main.model.Layout;
import com.bookmyshow.main.model.LayoutRow;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.SupportedCategory;
import com.bookmyshow.main.model.TimeSlot;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.AddressRepository;
import com.bookmyshow.main.repository.AmenityRepository;
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.service.VenueService;
import com.bookmyshow.main.events.NotificationEvent;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // Convert entity to DTO
    private VenueDTO entityToDto(Venue entity) {
        if (entity == null) {
            return null;
        }
        

        VenueDTO dto = new VenueDTO();
        dto.setId(entity.getId());
        dto.setVenueName(entity.getVenueName());
        dto.setVenueCapacity(entity.getVenueCapacity());
//        dto.setVenueFor(entity.getVenueFor());
        dto.setVenueType(entity.getVenueType());

        if (entity.getAddress() != null) {
            AddressDTO addressDto = new AddressDTO();
//            addressDto.setId(entity.getAddress().getId());
            addressDto.setStreet(entity.getAddress().getStreet());
            addressDto.setCity(entity.getAddress().getCity());
            addressDto.setPin(entity.getAddress().getPin());
            dto.setAddress(addressDto);
        }

        if (entity.getAmenities() != null) {
            List<String> amenityNames = entity.getAmenities().stream()
                    .map(Amenity::getAmenityName)
                    .collect(Collectors.toList());
            dto.setAmenities(amenityNames); 
        }
        
        if (entity.getTimeSlots() != null) {
            List<TimeSlotDTO> timeSlotDTOs = entity.getTimeSlots().stream().map(ts -> {
                TimeSlotDTO tsDto = new TimeSlotDTO();
                tsDto.setStartTime(ts.getStartTime());
                return tsDto;
            }).collect(Collectors.toList());
            dto.setTimeSlots(timeSlotDTOs);
        }

        
        
        
        if (entity.getSupportedCategories() != null) {
            List<String> supportedCategoryNames = entity.getSupportedCategories().stream()
                    .map(SupportedCategory::getCategoryname)
                    .collect(Collectors.toList());
            dto.setSupportedCategories(supportedCategoryNames);
        }

        if ("movies".equalsIgnoreCase(entity.getVenueFor()) && entity.getScreens() != null) {
            List<ScreenDTO> screenDTOs = entity.getScreens().stream().map(screen -> {
                ScreenDTO screenDto = new ScreenDTO();
                screenDto.setId(screen.getId());
                screenDto.setScreenName(screen.getScreenName());

                List<LayoutDTO> layoutDTOs = screen.getLayouts().stream().map(layout -> {
                    LayoutDTO layoutDto = new LayoutDTO();
                    layoutDto.setId(layout.getId());
                    layoutDto.setLayoutName(layout.getLayoutName());
                    layoutDto.setCols(layout.getCols());
                    layoutDto.setScreenId(screen.getId());
                    
                    if (layout.getLayoutRows() != null) {
                        List<String> rowStrings = layout.getLayoutRows().stream()
                            .map(LayoutRow::getRowName) 
                            .collect(Collectors.toList());
                        layoutDto.setRows(rowStrings);
                    }

                    return layoutDto;
                }).collect(Collectors.toList());

                screenDto.setLayouts(layoutDTOs);
                return screenDto;
            }).collect(Collectors.toList());

            dto.setScreens(screenDTOs);
        }

        return dto;
    }

    private Venue dtoToEntity(VenueDTO dto) {
        if (dto == null) {
            return null;
        }

        Venue entity = new Venue();
        entity.setVenueName(dto.getVenueName());
        entity.setVenueCapacity(dto.getVenueCapacity());
//        entity.setVenueFor(dto.getVenueFor());
        entity.setVenueType(dto.getVenueType());

        if (dto.getAmenities() != null) {
            List<Amenity> amenities = dto.getAmenities().stream()
                .map(amenityName -> {
                    Amenity amenity = new Amenity();
                    amenity.setAmenityName(amenityName);
                    return amenity;
                }).collect(Collectors.toList());
            entity.setAmenities(amenities);
        } else {
            entity.setAmenities(new ArrayList<>());
        }

        if (dto.getSupportedCategories() != null) {
            List<SupportedCategory> supportedCategories = dto.getSupportedCategories().stream()
                .map(categoryName -> {
                    SupportedCategory category = new SupportedCategory();
                    category.setCategoryname(categoryName);
                    return category;
                })
                .collect(Collectors.toList());
            entity.setSupportedCategories(supportedCategories);
        }
        
        if (dto.getTimeSlots() != null) {
            List<TimeSlot> timeSlots = dto.getTimeSlots().stream().map(tsDto -> {
                TimeSlot ts = new TimeSlot();
                ts.setStartTime(tsDto.getStartTime());
                ts.setVenue(entity);  
                return ts;
            }).collect(Collectors.toList());
            entity.setTimeSlots(timeSlots);
        } else {
            entity.setTimeSlots(new ArrayList<>());
        }


        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setPin(dto.getAddress().getPin());
            entity.setAddress(address);
        }

        if ("movies".equalsIgnoreCase(dto.getVenueName()) && dto.getScreens() != null) {
            List<Screen> screens = dto.getScreens().stream().map(screenDto -> {
                Screen screen = new Screen();
                screen.setScreenName(screenDto.getScreenName());

                if (screenDto.getLayouts() != null && !screenDto.getLayouts().isEmpty()) {
                    List<Layout> layouts = screenDto.getLayouts().stream().map(layoutDto -> {
                        Layout layout = new Layout();
                        layout.setLayoutName(layoutDto.getLayoutName());
                        layout.setCols(layoutDto.getCols());

                        if (layoutDto.getRows() != null && !layoutDto.getRows().isEmpty()) {
                            List<LayoutRow> layoutRows = layoutDto.getRows().stream().map(rowName -> {
                                LayoutRow layoutRow = new LayoutRow();
                                layoutRow.setRowName(rowName);
                                layoutRow.setLayout(layout);  
                                return layoutRow;
                            }).collect(Collectors.toList());
                            layout.setLayoutRows(layoutRows);
                        } else {
                            layout.setLayoutRows(new ArrayList<>()); 
                        }

                        return layout;
                    }).collect(Collectors.toList());
                    screen.setLayouts(layouts);
                } else {
                    screen.setLayouts(new ArrayList<>()); 
                }

                return screen;
            }).collect(Collectors.toList());
            entity.setScreens(screens);
        }

        return entity;
    }



    @Override
    public VenueDTO createVenue(VenueDTO dto) {
        Venue entity = dtoToEntity(dto);
        
        if (entity.getAddress() != null) {
            Address savedAddress = addressRepository.save(entity.getAddress());
            entity.setAddress(savedAddress); 
        }

        if ("movies".equalsIgnoreCase(entity.getVenueFor())) {
            if (entity.getScreens() == null) {
                entity.setScreens(new ArrayList<>());
            }

            for (Screen screen : entity.getScreens()) {
                screen.setVenue(entity);
                if (screen.getLayouts() != null) {
                    for (Layout layout : screen.getLayouts()) {
                        layout.setScreen(screen);
                        if (layout.getLayoutRows() != null) {
                            for (LayoutRow layoutRow : layout.getLayoutRows()) {
                                layoutRow.setLayout(layout);
                            }
                        }
                    }
                }
            }
        } else {
            entity.setScreens(new ArrayList<>());
        }
        
        if (entity.getTimeSlots() != null) {
            for (TimeSlot ts : entity.getTimeSlots()) {
                ts.setVenue(entity);
            }
        }

        Venue saved = venueRepository.save(entity);
        
        eventPublisher.publishEvent(new NotificationEvent(
                this,
                "New "+saved.getVenueType()+" Added",
                saved.getVenueName() + " is now available!",
                "VENUE"
        ));
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
        List<Venue> venues = Optional.ofNullable(venueRepository.findByAddressCity(city))
                                     .orElse(Collections.emptyList()); 
        return venues.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean softDeleteVenue(Long id) {
        return venueRepository.findById(id)
                .map(venue -> {
                    if (Boolean.TRUE.equals(venue.getDeleted())) {
                        throw new RuntimeException("Venue already deleted with id: " + id);
                    }
                    venue.setDeleted(true);
                    venueRepository.save(venue);
                    return true;
                })
                .orElseThrow(() -> new VenueNotFoundException("Venue not found with id: " + id));
    }
    
    
}
