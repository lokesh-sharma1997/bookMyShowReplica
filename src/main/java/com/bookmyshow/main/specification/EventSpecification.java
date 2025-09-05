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
	            for (Integer languageId : languages) {
	                Join<Event, Languages> join = root.join("languages", JoinType.INNER);
	                predicates.add(builder.equal(join.get("languageId"), languageId));
	            }
	        }

	       
	        if (genres != null && !genres.isEmpty()) {
	            for (Integer genreId : genres) {
	                Join<Event, Genres> join = root.join("genres", JoinType.INNER);
	                predicates.add(builder.equal(join.get("genreId"), genreId));
	            }
	        }

	        if (formats != null && !formats.isEmpty()) {
	            for (Integer formatId : formats) {
	                Join<Event, Format> join = root.join("format", JoinType.INNER);
	                predicates.add(builder.equal(join.get("formatId"), formatId));
	            }
	        }

	       
	        if (tags != null && !tags.isEmpty()) {
	            for (Integer tagId : tags) {
	                Join<Event, Tag> join = root.join("tag", JoinType.INNER);
	                predicates.add(builder.equal(join.get("tagId"), tagId));
	            }
	        }

	    
	        if (categories != null && !categories.isEmpty()) {
	            for (Integer categoryId : categories) {
	                Join<Event, Categories> join = root.join("categories", JoinType.INNER);
	                predicates.add(builder.equal(join.get("categoryId"), categoryId));
	            }
	        }

	  
	        if (price != null && !price.isEmpty()) {
	            for (Integer priceId : price) {
	                Join<Event, Price> join = root.join("price", JoinType.INNER);
	                predicates.add(builder.equal(join.get("priceId"), priceId));
	            }
	        }

	     
	        if (moreFilters != null && !moreFilters.isEmpty()) {
	            for (Integer filterId : moreFilters) {
	                Join<Event, MoreFilters> join = root.join("moreFilters", JoinType.INNER);
	                predicates.add(builder.equal(join.get("filterId"), filterId));
	            }
	        }

	       
	        if (releaseMonths != null && !releaseMonths.isEmpty()) {
	            for (Integer monthId : releaseMonths) {
	                Join<Event, ReleaseMonth> join = root.join("releaseMonth", JoinType.INNER);
	                predicates.add(builder.equal(join.get("releaseMonthId"), monthId));
	            }
	        }

	       
	        if (dateFilters != null && !dateFilters.isEmpty()) {
	            for (Integer dateId : dateFilters) {
	                Join<Event, DateFilter> join = root.join("dateFilter", JoinType.INNER);
	                predicates.add(builder.equal(join.get("dateFilterId"), dateId));
	            }
	        }

	        return builder.and(predicates.toArray(new Predicate[0]));
	    };
	}
}
