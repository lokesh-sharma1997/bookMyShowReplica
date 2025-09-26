package com.bookmyshow.main.Security;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.security.CustomUserDetailsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "testuser";
        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(123L);
        userMaster.setUsername(username);
        userMaster.setPassword("password123");

        Role role = new Role();
        role.setRoleName("ADMIN");
        userMaster.setRole(role);

        when(userRepository.findByUsername(username)).thenReturn(userMaster);

        UserDetails userDetails = service.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
            () -> service.loadUserByUsername(username));

        assertEquals("User not found with username: " + username, exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

//    @Test
//    void testLoadUserByUsername_NullRole_DefaultToUserRole() {
//        String username = "userWithNullRole";
//        UserMaster userMaster = new UserMaster();
//        userMaster.setUserId(456L);
//        userMaster.setUsername(username);
//        userMaster.setPassword("pass456");
//        userMaster.setRole(null);  // role is null here
//
//        when(userRepository.findByUsername(username)).thenReturn(userMaster);
//
//        UserDetails userDetails = service.loadUserByUsername(username);
//
//        assertNotNull(userDetails);
//        assertEquals(username, userDetails.getUsername());
//        // If your code does not handle null role, this test might fail - adjust accordingly
//        assertTrue(userDetails.getAuthorities().stream()
//            .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
//
//        verify(userRepository, times(1)).findByUsername(username);
//    }
}
