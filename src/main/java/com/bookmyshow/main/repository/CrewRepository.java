package com.bookmyshow.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Crew;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
	Optional<Crew> findByMemberName(String memberName);
}
