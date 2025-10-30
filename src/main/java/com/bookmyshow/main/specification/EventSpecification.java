package com.bookmyshow.main.specification;

import com.bookmyshow.main.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification {


	
	public static Specification<Event> filterEvents(
	        String type,
	        Integer cityid,
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
	        predicates.add(builder.isFalse(root.get("deleted")));
	        query.orderBy(builder.desc(root.get("eventId")));
	      
	        if (type != null && !type.isEmpty()) {
	            predicates.add(builder.equal(builder.lower(root.get("eventType")), type.toLowerCase()));
	        }
	        if (cityid != null ) {
	        	Join<Event, ?> cityJoin = root.join("city");
	            predicates.add(cityJoin.get("cityId").in(cityid));
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
	            Join<Event, Show> showJoin = root.join("shows", JoinType.INNER);

	            List<Predicate> pricePredicates = new ArrayList<>();

	            for (Integer id : price) {
	                switch (id) {
	                    case 1: // Free
	                        pricePredicates.add(builder.equal(showJoin.get("showPrice"), 0));
	                        break;
	                    case 2: // 0-500
	                        pricePredicates.add(builder.between(showJoin.get("showPrice"), 1, 500));
	                        break;
	                    case 3: // 501-2000
	                        pricePredicates.add(builder.between(showJoin.get("showPrice"), 501, 2000));
	                        break;
	                    case 4: // Above 2000
	                        pricePredicates.add(builder.greaterThan(showJoin.get("showPrice"), 2000));
	                        break;
	                }
	            }

	            if (!pricePredicates.isEmpty()) {
	                predicates.add(builder.or(pricePredicates.toArray(new Predicate[0])));
	            }
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
	            LocalDate today = LocalDate.now();
	            List<Predicate> datePredicates = new ArrayList<>();

	            for (Integer i : dateFilters) {
	                switch (i) {
	                    case 1: 
	                        datePredicates.add(
	                            builder.equal(root.get("startDate"), today)
	                        );
	                        break;
	                    case 2: 
	                        LocalDate tomorrow = today.plusDays(1);
	                        datePredicates.add(
	                            builder.equal(root.get("startDate"), tomorrow)
	                        );
	                        break;
	                    case 3: 
	                        LocalDate saturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
	                        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
	                        datePredicates.add(
	                            builder.between(root.get("startDate"), saturday, sunday)
	                        );
	                        break;
	                }
	            }
	            predicates.add(builder.or(datePredicates.toArray(new Predicate[0])));
	        }


	        return builder.and(predicates.toArray(new Predicate[0]));
	    };
	}
}