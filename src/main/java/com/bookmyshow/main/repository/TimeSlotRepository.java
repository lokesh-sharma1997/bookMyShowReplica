package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long>{

}
