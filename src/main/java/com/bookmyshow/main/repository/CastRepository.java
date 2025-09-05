package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Cast;
import java.util.Optional;


public interface CastRepository extends JpaRepository<Cast, Integer> {

	Optional<Cast> findByActorName(String actorName);

}
