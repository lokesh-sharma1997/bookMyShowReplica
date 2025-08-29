package com.bookmyshow.main.service;
 
import java.util.List;

import java.util.Optional;
 
import com.bookmyshow.main.dto.RoleDTO;
 
public interface RoleService {
 
    Optional<RoleDTO> getByRoleId(int roleId);
 
    Optional<RoleDTO> getByRoleName(String roleName);
 
    List<RoleDTO> getAllRoles();
 
  //  RoleDTO createRole(RoleDTO roleDTO);

  //  boolean deleteRole(int roleId);

}

 