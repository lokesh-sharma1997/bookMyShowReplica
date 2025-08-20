package com.bookmyshow.main.security;

import com.bookmyshow.main.model.User;
import com.bookmyshow.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // requires: User findByUsername(String username)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.bookmyshow.main.model.User u = userRepository.findByUsername(username);
        if (u == null) throw new UsernameNotFoundException("User not found: " + username);

        String role = (u.getRole() != null && u.getRole().getRoleName() != null)
                ? "ROLE_" + u.getRole().getRoleName()
                : "ROLE_USER";

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword()) // must be BCrypt encoded in DB
                .authorities(List.of(new SimpleGrantedAuthority(role)))
                .accountLocked(false)
                .disabled(Boolean.TRUE.equals(u.getDeleteFlag()))
                .build();
    }
}
