package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Cast;

public interface CastRepository extends JpaRepository<Cast, Integer> {

}
