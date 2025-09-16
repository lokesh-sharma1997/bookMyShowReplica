package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.RoleRepository;
import com.bookmyshow.main.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserMaster user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRoleId(1);
        role.setRoleName("USER");

        user = new UserMaster();
        user.setUserId(1L);
        user.setName("Kashish Saraswat");
        user.setUsername("kashish11");
        user.setPassword("securePass123");
        user.setEmail("kashishsaraswat@gmail.com");
        user.setPhoneNumber("9876543210");
        user.setRole(role);
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        user.setDeleteFlag(false);
    }

    @Test
    void testGetByUserIdFound() {
        when(userRepository.findByUserId(1L)).thenReturn(user);

        Optional<UserDTO> result = userService.getByUserId(1);

        assertTrue(result.isPresent());
        assertEquals("kashish11", result.get().getUsername());
        verify(userRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetByUserIdNotFound() {
        when(userRepository.findByUserId(99L)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getByUserId(99));
    }

    @Test
    void testGetByUsernameFound() {
        when(userRepository.findByUsername("kashish11")).thenReturn(user);

        Optional<UserDTO> result = userService.getByUsername("kashish11");

        assertTrue(result.isPresent());
        assertEquals("Kashish Saraswat", result.get().getName());
    }

    @Test
    void testGetByUsernameNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        Optional<UserDTO> result = userService.getByUsername("unknown");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetByRoleFound() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.findByRole(role)).thenReturn(List.of(user));

        List<UserDTO> result = userService.getByRole("ADMIN");

        assertEquals(1, result.size());
        assertEquals("kashish11", result.get(0).getUsername());
    }

    @Test
    void testGetByRoleNotFound() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userService.getByRole("ADMIN"));
    }

    @Test
    void testGetAllUsers() {
        UserMaster deletedUser = new UserMaster();
        deletedUser.setUserId(2L);
        deletedUser.setName("Deleted User");
        deletedUser.setDeleteFlag(true);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user, deletedUser));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("kashish11", result.get(0).getUsername());
    }

    @Test
    void testDeleteById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.deleteById(1);

        assertTrue(result);
        assertTrue(user.getDeleteFlag());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteById_AlreadyDeleted() {
        user.setDeleteFlag(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> userService.deleteById(1));
    }

    @Test
    void testDeleteById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteById(99));
    }

    @Test
    void testUpdateUserRole_Success() {
        Role adminRole = new Role();
        adminRole.setRoleId(2);
        adminRole.setRoleName("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2)).thenReturn(Optional.of(adminRole));

        userService.updateUserRole(1);

        assertEquals("ADMIN", user.getRole().getRoleName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserRole_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUserRole(99));
    }

    @Test
    void testUpdateUserRole_RoleNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userService.updateUserRole(1));
    }

    @Test
    void testSearchUser() {
        when(userRepository.globalSearch("kashish")).thenReturn(List.of(user));

        List<UserDTO> result = userService.searchUser("kashish");

        assertEquals(1, result.size());
        assertEquals("kashish11", result.get(0).getUsername());
    }
}