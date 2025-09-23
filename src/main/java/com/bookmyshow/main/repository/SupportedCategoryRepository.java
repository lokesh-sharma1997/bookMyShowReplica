package com.bookmyshow.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.model.Show;
import com.bookmyshow.main.model.SupportedCategory;

public interface SupportedCategoryRepository extends JpaRepository<SupportedCategory,Long>{

	static Optional<Event> findByShowAndLayoutName(Show savedShow, String layoutname) {
		return null;
	}

}
