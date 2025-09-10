package com.bookmyshow.main.specification;

import com.bookmyshow.main.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EventSpecification {
	
	public static Specification<Event> filterEvents(
	        String type,
	        List<Integer> languages,
	        List<Integer> genres,
	        List<Integer> formats,
	        List<Integer> tags,
	        List<Integer> categories,
	        List<Integer> price,
	        List<Integer> moreFilters,
	        List<Integer> releaseMonths,
	        List<Integer> dateFilters
	) {
	    return (root, query, builder) -> {
	        List<Predicate> predicates = new ArrayList<>();

	       
	        query.distinct(true);

	      
	        if (type != null && !type.isEmpty()) {
	            predicates.add(builder.equal(builder.lower(root.get("eventType")), type.toLowerCase()));
	        }

	     
	        if (languages != null && !languages.isEmpty()) {
	        	Join<Event, ?> languageJoin = root.join("languages");
	            predicates.add(languageJoin.get("languageId").in(languages));
	        }

	       
	        if (genres != null && !genres.isEmpty()) {
	        	Join<Event, ?> genreJoin = root.join("genres");
	            predicates.add(genreJoin.get("genreId").in(genres));
	        }

	        if (formats != null && !formats.isEmpty()) {
	        	Join<Event, ?> formatJoin = root.join("format");
	            predicates.add(formatJoin.get("formatId").in(formats));
	        }

	       
	        if (tags != null && !tags.isEmpty()) {
	        	Join<Event, ?> tagJoin = root.join("tag");
	            predicates.add(tagJoin.get("tagId").in(tags));
	        }

	    
	        if (categories != null && !categories.isEmpty()) {
	        	Join<Event, ?> categoryJoin = root.join("categories");
	            predicates.add(categoryJoin.get("categoryId").in(categories));
	        }

	  
	        if (price != null && !price.isEmpty()) {
	        	Join<Event, ?> priceJoin = root.join("price");
	            predicates.add(priceJoin.get("priceId").in(price));
	        }

	     
	        if (moreFilters != null && !moreFilters.isEmpty()) {
	        	   Join<Event, ?> moreFilterJoin = root.join("moreFilters");
		            predicates.add(moreFilterJoin.get("filterId").in(moreFilters));
	        }

	       
	        if (releaseMonths != null && !releaseMonths.isEmpty()) {
	        	Join<Event, ?> releaseMonthJoin = root.join("releaseMonth");
	            predicates.add(releaseMonthJoin.get("releaseMonthId").in(releaseMonths));
	        }

	       
	        if (dateFilters != null && !dateFilters.isEmpty()) {
	        	Join<Event, ?> dateFilterJoin = root.join("dateFilter");
	            predicates.add(dateFilterJoin.get("dateFilterId").in(dateFilters));
	        }

	        return builder.and(predicates.toArray(new Predicate[0]));
	    };
	}
}
