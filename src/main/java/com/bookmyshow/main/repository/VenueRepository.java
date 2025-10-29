package com.bookmyshow.main.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.bookmyshow.main.model.Venue;

import java.util.List;
 

public interface VenueRepository extends JpaRepository<Venue, Long> {
	@Query("SELECT v FROM Venue v WHERE LOWER(v.address.city.name) = LOWER(:city)AND v.deleted = false")
    List<Venue> findByCity(@Param("city") String city);
    List<Venue> findByAddress_City_NameAndDeletedFalse(String cityName);

    List<Venue> findByVenueFor(String venuefor);
	
	

}
