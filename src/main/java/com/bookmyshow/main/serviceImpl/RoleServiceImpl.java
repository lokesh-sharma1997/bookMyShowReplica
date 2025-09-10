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
		return roleRepository.findByRoleName(roleName.toUpperCase()).map(this::convertToDTO).or(() -> {
			throw new RoleNotFoundException("Role not found: " + roleName);
		});
	}
 
	@Override
	public List<RoleDTO> getAllRoles() {
		return roleRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}
 
	
	  @Override public RoleDTO createRole(RoleDTO roleDTO) { Role role =
	  convertToEntity(roleDTO); Role savedRole = roleRepository.save(role); return
	  convertToDTO(savedRole); }
	 
	 /* @Override public boolean deleteRole(int roleId) { if
	 * (roleRepository.existsById(roleId)) { roleRepository.deleteById(roleId);
	 * return true; } return false; }
	 */
 
	private RoleDTO convertToDTO(Role role) {
		RoleDTO dto = new RoleDTO();
		dto.setRoleId(role.getRoleId());
		dto.setRoleName(role.getRoleName()); // Already a String now
		return dto;
	}
 
	private Role convertToEntity(RoleDTO dto) {
		Role role = new Role();
		role.setRoleId(dto.getRoleId());
		if (dto.getRoleName() != null) {
			role.setRoleName(dto.getRoleName().toUpperCase()); // Store uppercase for consistency
		}
		return role;
	}
 
}
 
 