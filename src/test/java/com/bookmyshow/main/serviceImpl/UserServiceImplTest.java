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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserMaster user;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new Role();
        role.setRoleId(1);
        role.setRoleName("USER");

        user = new UserMaster();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setEmail("john@example.com");
        user.setPhoneNumber("1234567890");
        user.setRole(role);
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        user.setDeleteFlag(false);
    }

    // getByUserId success
    @Test
    void testGetByUserId_Success() {
        when(userRepository.findByUserId(1)).thenReturn(user);

        Optional<UserDTO> result = userService.getByUserId(1);

        assertTrue(result.isPresent());
        assertEquals("johndoe", result.get().getUsername());
    }

    // getByUserId not found
    @Test
    void testGetByUserId_NotFound() {
        when(userRepository.findByUserId(2)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getByUserId(2));
    }

    // getByUsername success
    @Test
    void testGetByUsername_Success() {
        when(userRepository.findByUsername("johndoe")).thenReturn(user);

        Optional<UserDTO> result = userService.getByUsername("johndoe");

        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().getEmail());
    }

    // getByUsername not found
    @Test
    void testGetByUsername_NotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        Optional<UserDTO> result = userService.getByUsername("unknown");

        assertTrue(result.isEmpty());
    }

    // getByRole success
    @Test
    void testGetByRole_Success() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.findByRole(role)).thenReturn(List.of(user));

        List<UserDTO> result = userService.getByRole("ADMIN");

        assertEquals(1, result.size());
        assertEquals("johndoe", result.get(0).getUsername());
    }
    
    @Test
    void testGetByRole_FilterOutDeletedUsers() {
        user.setDeleteFlag(true); // simulate deleted user
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.findByRole(role)).thenReturn(List.of(user));

        List<UserDTO> result = userService.getByRole("ADMIN");

        assertTrue(result.isEmpty()); // should not return deleted user
    }

    @Test
    void testGetAllUsers_FilterOutDeletedUsers() {
        UserMaster activeUser = new UserMaster();
        activeUser.setUserId(1L);
        activeUser.setName("Active");
        activeUser.setDeleteFlag(false);

        UserMaster deletedUser = new UserMaster();
        deletedUser.setUserId(2L);
        deletedUser.setName("Deleted");
        deletedUser.setDeleteFlag(true);

        when(userRepository.findAll()).thenReturn(List.of(activeUser, deletedUser));

        List<UserDTO> result = userService.getAllUsers();

        // Only active user should be returned
        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).getName());
    }


    // getByRole role not found
    @Test
    void testGetByRole_RoleNotFound() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userService.getByRole("ADMIN"));
    }

    // getAllUsers success
    @Test
    void testGetAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    // getAllUsers empty list
    @Test
    void testGetAllUsers_Empty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserDTO> result = userService.getAllUsers();

        assertTrue(result.isEmpty());
    }

    // deleteById success
    @Test
    void testDeleteById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserMaster.class))).thenReturn(user);

        boolean result = userService.deleteById(1);

        assertTrue(result);
        assertTrue(user.getDeleteFlag());
    }

    // deleteById already deleted
    @Test
    void testDeleteById_AlreadyDeleted() {
        user.setDeleteFlag(true);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.deleteById(1));
        assertEquals("User already deleted with ID: 1", ex.getMessage());
    }

    // deleteById not found
    @Test
    void testDeleteById_NotFound() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteById(2));
    }

    // updateUserRole success
    @Test
    void testUpdateUserRole_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Role adminRole = new Role();
        adminRole.setRoleId(2);
        adminRole.setRoleName("ADMIN");
        when(roleRepository.findById(2)).thenReturn(Optional.of(adminRole));

        userService.updateUserRole(1);

        assertEquals("ADMIN", user.getRole().getRoleName());
        verify(userRepository, times(1)).save(user);
    }

    // updateUserRole user not found
    @Test
    void testUpdateUserRole_UserNotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUserRole(99));
    }

    // updateUserRole role not found
    @Test
    void testUpdateUserRole_RoleNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userService.updateUserRole(1));
    }

    // searchUser success
    @Test
    void testSearchUser_Success() {
        when(userRepository.globalSearch("john")).thenReturn(List.of(user));

        List<UserDTO> result = userService.searchUser("john");

        assertEquals(1, result.size());
        assertEquals("johndoe", result.get(0).getUsername());
    }

    // searchUser empty
    @Test
    void testSearchUser_Empty() {
        when(userRepository.globalSearch("xyz")).thenReturn(Collections.emptyList());

        List<UserDTO> result = userService.searchUser("xyz");

        assertTrue(result.isEmpty());
    }
}
