package com.bookmyshow.main.service;

import java.util.List;

import com.bookmyshow.main.model.Role;

public interface RoleService
{
	Role getByRoleId(String roleId);
	
	List<Role> getByRoleName(String roleName);
}
