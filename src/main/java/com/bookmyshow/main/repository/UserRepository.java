package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster, Integer> {
	UserMaster findByUserId(int userId);

	UserMaster findByUsername(String username);

	List<UserMaster> findByRole(Role role);

	List<UserMaster> findByNameIgnoreCaseOrUsernameIgnoreCaseOrPhoneNumberOrEmailIgnoreCase(String name,
			String username, String phoneNumber, String email);

	boolean existsByUsername(String username);

	boolean deleteByUserId(int id);

}
