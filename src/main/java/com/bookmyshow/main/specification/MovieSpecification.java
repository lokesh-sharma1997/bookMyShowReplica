package com.bookmyshow.main.specification;



import org.springframework.data.jpa.domain.Specification;
import com.bookmyshow.main.model.Event;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Join;

import java.util.ArrayList;
import java.util.List;

public class MovieSpecification {

    public static Specification<Event> filterEvents(
            List<String> languages,
            List<String> genres,
            List<String> formats,
            String releaseMonth
    ) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (languages != null && !languages.isEmpty()) {
                Join<Event, String> languageJoin = root.join("language");
                predicates.add(languageJoin.in(languages));
            }

            if (genres != null && !genres.isEmpty()) {
                Join<Event, String> genreJoin = root.join("genre");
                predicates.add(genreJoin.in(genres));
            }

            if (formats != null && !formats.isEmpty()) {
                Join<Event, String> formatJoin = root.join("format");
                predicates.add(formatJoin.in(formats));
            }

            if (releaseMonth != null && !releaseMonth.isEmpty()) {
                predicates.add(builder.equal(
                        builder.function("MONTH", Integer.class, root.get("releaseDate")),
                        Integer.parseInt(releaseMonth)
                ));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
