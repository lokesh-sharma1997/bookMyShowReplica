package com.bookmyshow.main.repository;



import com.bookmyshow.main.model.Movie;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByName(String name);

    List<Movie> findAll(Specification<Movie> spec);

	Optional<Movie> findByDeletedFalse();

	

}
