package com.bookmyshow.main.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Theatre;

import java.util.List;
 

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
	 List<Theatre> findBynameIgnoreCase(String name);
}
