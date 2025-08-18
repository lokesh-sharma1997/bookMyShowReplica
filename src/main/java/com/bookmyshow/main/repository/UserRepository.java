package com.bookmyshow.main.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	User findByUserId(int userId);

	List<User> findByNameUser(String name);

	User findByUsernameUser(String username);

	User findByEmailUser(String email);

	List<User> findByRole(String role);

	User findByPhoneNumberUser(long phoneNumber);

	List<User> findByCreatedOnDateUser(LocalDate localDate);

	List<User> findByUpdatedOnDateUser(LocalDate localDate);

}
