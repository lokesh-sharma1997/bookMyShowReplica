package com.bookmyshow.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	User findByUserId(int userId);

	List<User> findByName(String name);

	User findByUsername(String username);

	Optional<User> findByEmailIgnoreCase(String email);


	List<User> findByRole(Role role);

	User findByPhoneNumber(long phoneNumber);
	
	 boolean existsByUsername(String username);
	 boolean deleteByUserId(int id);

	 
	 
}
