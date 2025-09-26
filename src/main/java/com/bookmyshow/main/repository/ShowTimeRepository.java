package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.ShowTimeDate;

public interface ShowTimeRepository extends JpaRepository<ShowTimeDate, Long>{

}
