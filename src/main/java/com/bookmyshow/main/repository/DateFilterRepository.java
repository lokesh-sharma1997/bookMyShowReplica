package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.DateFilter;

public interface DateFilterRepository extends JpaRepository<DateFilter, Integer> {

}
