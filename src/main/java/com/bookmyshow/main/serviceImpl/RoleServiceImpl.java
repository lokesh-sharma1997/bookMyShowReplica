package com.bookmyshow.main.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.RoleDTO;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.repository.RoleRepository;
import com.bookmyshow.main.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<RoleDTO> getByRoleId(int roleId) {
        return roleRepository.findById(roleId).map(this::convertToDTO);
    }

    @Override
    public Optional<RoleDTO> getByRoleName(String roleName) {
        try {
            Role.RoleName roleEnum = Role.RoleName.valueOf(roleName.toUpperCase());
            return roleRepository.findByRoleName(roleEnum)
                    .map(this::convertToDTO)
                    .or(() -> { throw new RoleNotFoundException("Role not found: " + roleName); });
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException("Invalid role name: " + roleName);
        }
    }



    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
/**
    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = convertToEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public boolean deleteRole(int roleId) {
        if (roleRepository.existsById(roleId)) {
            roleRepository.deleteById(roleId);
            return true;
        }
        return false;
    }
**/
    // ========================
    // Helper conversion methods
    // ========================

    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName().name()); // enum → String
        return dto;
    }

    private Role convertToEntity(RoleDTO dto) {
        Role role = new Role();
        role.setRoleId(dto.getRoleId());
        if (dto.getRoleName() != null) {
            role.setRoleName(Role.RoleName.valueOf(dto.getRoleName().toUpperCase())); // String → enum
        }
        return role;
    }

}
