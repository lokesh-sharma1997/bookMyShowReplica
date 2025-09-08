package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

}
