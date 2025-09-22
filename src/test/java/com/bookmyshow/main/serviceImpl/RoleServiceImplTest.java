package com.bookmyshow.main.serviceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookmyshow.main.dto.RoleDTO;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRoleId(1);
        role.setRoleName("USER");
    }

    @Test
    void testGetByRoleIdFound() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        Optional<RoleDTO> result = roleService.getByRoleId(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getRoleId());
        assertEquals("USER", result.get().getRoleName());
    }

    @Test
    void testGetByRoleIdNotFound() {
        when(roleRepository.findById(99)).thenReturn(Optional.empty());

        Optional<RoleDTO> result = roleService.getByRoleId(99);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetByRoleNameFound() {
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));

        Optional<RoleDTO> result = roleService.getByRoleName("user");

        assertTrue(result.isPresent());
        assertEquals("USER", result.get().getRoleName());
    }

    @Test
    void testGetByRoleNameNotFound() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> {
            roleService.getByRoleName("admin");
        });
    }
}
