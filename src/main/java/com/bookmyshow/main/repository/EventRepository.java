package com.bookmyshow.main.repository;

import com.bookmyshow.main.model.Event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String name);

    List<Event> findAll(Specification<Event> spec);

	List<Event> findByDeletedFalse();
	List<Event> findByDeletedFalseAndContentTypeIgnoreCase(String contentType);
	
	List<Event> findTop10ByContentTypeOrderByReleaseDateDesc(String contentType);

	List<Event> findTop10ByOrderByReleaseDateDesc();
	
//	List<Event> findByCategoryTypeIgnoreCase(String categoryType);


}
