package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.dto.ShowDTO;
import com.bookmyshow.main.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

	Long save(ShowDTO showDTO);


}
