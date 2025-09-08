package com.bookmyshow.main.specification;

import com.bookmyshow.main.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EventSpecification {

    public static Specification<Event> hasLanguages(List<String> languages) {
        return (root, query, cb) -> {
            if (languages == null || languages.isEmpty()) return null;
            Join<Event, Languages> join = root.join("languages", JoinType.INNER);
            return join.get("languageName").in(languages);
        };
    }

    public static Specification<Event> hasGenres(List<String> genres) {
        return (root, query, cb) -> {
            if (genres == null || genres.isEmpty()) return null;
            Join<Event, Genres> join = root.join("genres", JoinType.INNER);
            return join.get("genresName").in(genres);
        };
    }

    public static Specification<Event> hasFormats(List<String> formats) {
        return (root, query, cb) -> {
            if (formats == null || formats.isEmpty()) return null;
            Join<Event, Format> join = root.join("format", JoinType.INNER);
            return join.get("formatName").in(formats);
        };
    }

    public static Specification<Event> hasReleaseMonth(String releaseMonth) {
        return (root, query, cb) -> {
            if (releaseMonth == null || releaseMonth.isEmpty()) return null;
            Join<Event, ReleaseMonth> join = root.join("releaseMonth", JoinType.INNER);
            return cb.equal(join.get("releaseMonthName"), releaseMonth);
        };
    }

    public static Specification<Event> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }
}
