package com.bookmyshow.main.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.repository.RoleRepository;
import com.bookmyshow.main.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService
{
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role getByRoleId(String roleId) {
		return roleRepository.findByRoleId(roleId);
	}

	@Override
	public List<Role> getByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

}
