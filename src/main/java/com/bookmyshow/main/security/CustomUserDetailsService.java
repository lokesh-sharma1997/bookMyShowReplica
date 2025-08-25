package com.bookmyshow.main.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.JwtDTO;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // requires: User findByUsername(String username)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMaster data = userRepository.findByUsername(username);
        JwtDTO jwtDTO = new JwtDTO();
        jwtDTO.setUsername(data.getUsername());
        jwtDTO.setPassword(data.getPassword());
        jwtDTO.setRoleName(String.valueOf(data.getRole().getRoleName()));
        if (jwtDTO != null) {
            return User.builder()
                    .username(jwtDTO.getUsername())
                    .password(jwtDTO.getPassword())
                    .roles(jwtDTO.getRoleName())
                    .build();
        }
        throw new RuntimeException("User not found with username: " + username);

    }
}
