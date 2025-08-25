package com.bookmyshow.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster, Integer>
{
    UserMaster findByUserId(int userId);

	List<UserMaster> findByName(String name);

    UserMaster findByUsername(String username);

	Optional<UserMaster> findByEmailIgnoreCase(String email);


	List<UserMaster> findByRole(Role role);

    UserMaster findByPhoneNumber(long phoneNumber);
	
	 boolean existsByUsername(String username);
	 boolean deleteByUserId(int id);

	 
	 
}
