package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.RoleDTO;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role testRole;
    private RoleDTO testRoleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testRole = new Role();
        testRole.setRoleId(1);
        testRole.setRoleName("ADMIN");

        testRoleDTO = new RoleDTO();
        testRoleDTO.setRoleId(1);
        testRoleDTO.setRoleName("ADMIN");
    }

    @Test
    void testGetByRoleId_WhenExists() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(testRole));

        Optional<RoleDTO> result = roleService.getByRoleId(1);

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getRoleName());
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    void testGetByRoleId_WhenNotExists() {
        when(roleRepository.findById(999)).thenReturn(Optional.empty());

        Optional<RoleDTO> result = roleService.getByRoleId(999);

        assertFalse(result.isPresent());
        verify(roleRepository).findById(999);
    }

    @Test
    void testGetByRoleName_WhenExists() {
        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.of(testRole));

        Optional<RoleDTO> result = roleService.getByRoleName("admin");

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getRoleId());
        verify(roleRepository).findByRoleName("ADMIN");
    }

    @Test
    void testGetByRoleName_WhenNotExists() {
        when(roleRepository.findByRoleName("MANAGER")).thenReturn(Optional.empty());

        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class,
                () -> roleService.getByRoleName("manager"));

        assertEquals("Role not found: manager", exception.getMessage());
        verify(roleRepository).findByRoleName("MANAGER");
    }

    @Test
    void testGetAllRoles() {
        List<Role> roles = Arrays.asList(testRole);
        when(roleRepository.findAll()).thenReturn(roles);

        List<RoleDTO> result = roleService.getAllRoles();

        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getRoleName());
        verify(roleRepository).findAll();
    }
    
    @Test
    void testCreateRoleWithNullName() {
        RoleDTO inputDto = new RoleDTO();
        inputDto.setRoleId(2);
        inputDto.setRoleName(null); // this covers the else path

        Role entity = new Role();
        entity.setRoleId(2);
        entity.setRoleName(null);

        when(roleRepository.save(any(Role.class))).thenReturn(entity);

        RoleDTO result = roleService.createRole(inputDto);

        assertNotNull(result);
        assertEquals(2, result.getRoleId());
        assertNull(result.getRoleName());

        verify(roleRepository).save(any(Role.class));
    }
    
    @Test
    void testCreateRole_withRoleName_shouldConvertToUpperCase() {
        RoleDTO inputDto = new RoleDTO();
        inputDto.setRoleId(1);
        inputDto.setRoleName("admin");

        Role savedEntity = new Role();
        savedEntity.setRoleId(1);
        savedEntity.setRoleName("ADMIN");

        when(roleRepository.save(any(Role.class))).thenReturn(savedEntity);

        RoleDTO result = roleService.createRole(inputDto);

        assertNotNull(result);
        assertEquals("ADMIN", result.getRoleName());
        verify(roleRepository).save(any(Role.class));
    }


}
