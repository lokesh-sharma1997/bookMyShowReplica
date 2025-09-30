package com.bookmyshow.main.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.bookmyshow.main.model.Venue;

import java.util.List;
 

public interface VenueRepository extends JpaRepository<Venue, Long> {
	@Query("SELECT v FROM Venue v WHERE LOWER(v.address.city) = LOWER(:city)")
    List<Venue> findByCity(@Param("city") String city);
	public List<Venue> findByAddress_City_Name(String cityName);

    List<Venue> findByVenueFor(String venuefor);
	
	

}
