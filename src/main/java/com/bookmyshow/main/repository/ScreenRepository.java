package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Screen;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

}
