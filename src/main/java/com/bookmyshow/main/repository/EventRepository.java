package com.bookmyshow.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmyshow.main.model.Event;


public interface EventRepository extends JpaRepository<Event, Long> {
	@Query("SELECT e FROM Event e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) AND e.deleted = false")
	List<Event> searchByNameOnly(@Param("name") String name);

    Optional<Event> findByNameIgnoreCaseAndEventType(String name, String eventType);
    @Query("SELECT e FROM Event e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(e.eventType) IN :eventTypes AND e.deleted = false")
    List<Event> searchByNameAndEventTypes(@Param("name") String name, @Param("eventTypes") List<String> eventTypes);



    List<Event> findAll(Specification<Event> spec);
    
    List<Event> findByEventType(String eventType);

   
	
	List<Event> findTop10ByEventTypeOrderByReleasingOnDesc(String eventType);

	List<Event> findTop10ByOrderByReleasingOnDesc();
	
	Page<Event> findAll(Specification<Event> spec, Pageable pageable);





}
