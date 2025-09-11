package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.SupportedCategory;

public interface SupportedCategoryRepository extends JpaRepository<SupportedCategory,Long>{

}
