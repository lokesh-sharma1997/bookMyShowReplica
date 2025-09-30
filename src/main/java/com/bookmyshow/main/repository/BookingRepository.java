package com.bookmyshow.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookmyshow.main.model.Booking;
import com.bookmyshow.main.model.UserMaster;

import io.lettuce.core.dynamic.annotation.Param;

public interface BookingRepository extends JpaRepository<Booking,Long> {

	Optional<UserMaster> findByShowId(Long showId);

}
	