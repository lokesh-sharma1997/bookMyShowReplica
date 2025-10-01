package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Tag;

public interface TagRepository  extends JpaRepository<Tag, Integer>{

}
