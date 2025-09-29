package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
