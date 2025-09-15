package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Amenity;


public interface AmenityRepository extends JpaRepository<Amenity,Long>{

     List<Amenity> findByAmenityNameIn(List<String> amenityNames);


}
