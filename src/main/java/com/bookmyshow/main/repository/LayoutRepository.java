package com.bookmyshow.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Layout;


public interface LayoutRepository extends JpaRepository<Layout,Long>{


	Optional<Layout> findByLayoutName(String layoutName);

}
