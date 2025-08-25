package com.bookmyshow.main.Specification;



import org.springframework.data.jpa.domain.Specification;
import com.bookmyshow.main.model.Movie;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Join;

import java.util.ArrayList;
import java.util.List;

public class MovieSpecification {

    public static Specification<Movie> filterMovies(
            List<String> languages,
            List<String> genres,
            List<String> formats,
            String releaseMonth
    ) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (languages != null && !languages.isEmpty()) {
                Join<Movie, String> languageJoin = root.join("language");
                predicates.add(languageJoin.in(languages));
            }

            if (genres != null && !genres.isEmpty()) {
                Join<Movie, String> genreJoin = root.join("genre");
                predicates.add(genreJoin.in(genres));
            }

            if (formats != null && !formats.isEmpty()) {
                Join<Movie, String> formatJoin = root.join("format");
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
