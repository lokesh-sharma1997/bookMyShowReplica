package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster, Integer> {
	UserMaster findByUserId(int userId);

	UserMaster findByUsername(String username);

	List<UserMaster> findByRole(Role role);

	boolean existsByUsername(String username);

	boolean deleteByUserId(int id);
	
	@Query("SELECT user FROM UserMaster user WHERE " +
		       "(LOWER(user.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(user.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(user.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "user.phoneNumber LIKE CONCAT('%', :keyword, '%')) AND " +
		       "user.deleteFlag = false")
		List<UserMaster> globalSearch(@Param("keyword") String value);




}