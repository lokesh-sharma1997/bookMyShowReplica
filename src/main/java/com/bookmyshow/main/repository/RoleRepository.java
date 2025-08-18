package com.bookmyshow.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByRoleId(String roleId);

	List<Role> findByRoleName(String roleName);

}
