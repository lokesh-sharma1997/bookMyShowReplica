package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Genres;

public interface GenresRepository  extends JpaRepository<Genres, Integer> {

}
