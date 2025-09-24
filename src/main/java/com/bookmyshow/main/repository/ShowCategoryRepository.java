package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.ShowCategory;

public interface ShowCategoryRepository extends JpaRepository<ShowCategory, Long> {
//	List<Seat> findByScreen_Id(Long id);

	List<ShowCategory> findByShowId(Long id);



}