package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.Show;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByScreenId(Long id);

	

	static List<Seat> findByShowCategoryId(Long id) {
		return null;
	}



	List<Seat> findByShowCategoryIdAndReservedTrue(Long id);






	List<Seat> findByShowIdAndScreenId(Long id, Long id2);




}
