package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.EventDTO;
import com.bookmyshow.main.dto.ShowDTO;
import com.bookmyshow.main.dto.SupportedCategoryDTO;
import com.bookmyshow.main.model.*;
import com.bookmyshow.main.repository.ShowRepository;
import com.bookmyshow.main.repository.LayoutRepository;
import com.bookmyshow.main.repository.SeatRepository;
import com.bookmyshow.main.repository.SupportedCategoryRepository;
import com.bookmyshow.main.service.ShowService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private LayoutRepository layoutRepository;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
	private ModelMapper mapper;
    @Autowired
    private SupportedCategoryRepository supportedCategoryRepository;
    private Show toEntity(ShowDTO dto) {
		return mapper.map(dto, Show.class);
	}
    @Override
    public Long createShow(ShowDTO showDTO) {
        Show show = toEntity(showDTO);

        show.setEventid(Long.valueOf(showDTO.getEventid()));
        show.setVenueid(Long.valueOf(showDTO.getVenueid())); 
        show.setCity(showDTO.getCity());
        show.setDate(showDTO.getDate());
        show.setStartTime(showDTO.getStartTime());
        show.setDuration(showDTO.getDuration());

        List<Showprice> showPrices = new ArrayList<>();
        
        if ("Movie".equalsIgnoreCase(showDTO.getEventType())) {
            show.setShowPrices(null); 
        } else {
            for (Integer price : showDTO.getShowprice()) {
                Showprice showPrice = new Showprice();
                showPrice.setPrice(price);
                showPrice.setShow(show); 
                showPrices.add(showPrice);
            }
            show.setShowPrices(showPrices); 
        }

        // Set the language(s)
        List<Languages> languagesList = new ArrayList<>();
        languagesList.add(new Languages());  
        show.setLanguages(languagesList);
        show.setStatus(showDTO.getStatus());

        if ("movies".equalsIgnoreCase(showDTO.getEventType())) {
            show.setEventType("movies");
            show.setFormat(showDTO.getFormat()); 

//            // Process layouts (only for movies) and set them to the show
//            List<Layout> layouts = processLayouts(showDTO.getSupportedCategories());
//            show.setLayouts(layouts);  // Assign the processed layouts

        } else {
            // For non-movie events (like concerts or plays), set event type
            show.setEventType(showDTO.getEventType());  
            show.setFormat(null);  
            show.setScreen(null); 
        }

        return showRepository.save(showDTO);
    }

//    // Method to process layouts (only relevant for movies)
//    private List<Layout> processLayouts(List<SupportedCategoryDTO> supportedCategories) {
//        List<Layout> layouts = new ArrayList<>();
//
//        // Check if supported categories are provided
//        if (supportedCategories != null && !supportedCategories.isEmpty()) {
//            for (SupportedCategoryDTO category : supportedCategories) {
//                Layout layout = new Layout();
//                layout.setLayoutName(category.getLayoutName());  // Layout name (e.g., "Premium")
//                layout.setRows(category.getRows());  // Rows in the layout (e.g., ["A", "B", "C"])
//                layout.setCols(Integer.parseInt(category.getCols()));  // Number of columns (e.g., 20)
//                layout.setPrice(category.getPrice());  // Price for the layout
//
//                // Save Layout to the database (optional, if needed)
//                layouts.add(layoutRepository.save(layout));  // Save layout to the database (if needed)
//            }
//        }
//
//        return layouts;
//    }
}
