package com.bookmyshow.main.repository;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookmyshow.main.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser_UserId(Long userId);
}

