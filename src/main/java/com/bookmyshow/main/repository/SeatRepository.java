package com.bookmyshow.main.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bookmyshow.main.model.Seat;
import jakarta.persistence.LockModeType;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	
	List<Seat> findByScreenId(Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Seat s WHERE s.screen.id = :screenId")
	List<Seat> findByScreenIdWithLock(@Param("screenId") Long screenId);

	@Query("SELECT s FROM Seat s JOIN s.layoutRow lr WHERE lr.layout.id = :layoutId")
	List<Seat> findByLayoutId(@Param("layoutId") Long layoutId);

	@Query("SELECT s FROM Seat s WHERE s.screen.id = :screenId AND s.seatNumber IN :seatNumbers")
	List<Seat> findBySeatNumberAndScreenId(@Param("screenId") Long screenId, @Param("seatNumbers") List<String> seatNumbers);

	@Query("SELECT s FROM Seat s WHERE s.screen.id = :screenId AND UPPER(TRIM(s.seatNumber)) IN :normalizedSeatNumbers")
	List<Seat> findSeatsByNumberAndScreenIdWithoutStatusCheck(@Param("screenId") Long screenId, @Param("normalizedSeatNumbers") List<String> normalizedSeatNumbers);
}
