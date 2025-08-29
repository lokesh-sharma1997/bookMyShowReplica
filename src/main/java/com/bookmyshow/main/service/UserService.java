package com.bookmyshow.main.service;
 
import java.util.List;
import java.util.Optional;
 
import com.bookmyshow.main.dto.UserDTO;
 
 
public interface UserService
{
	Optional<UserDTO> getByUserId(int userId);
 
	List<UserDTO> getByName(String name);
 
    Optional<UserDTO> getByUsername(String username);
 
    Optional<UserDTO> getByEmail(String email);
 
    List<UserDTO> getByRole(String roleName);
 
    Optional<UserDTO> getByPhoneNumber(String phone);
 
    List<UserDTO> getAllUsers();
 
    boolean deleteById(int userId);
    
    void updateUserRole(int userId, String roleName);
 
    boolean userExistsByUsername(String username);
}
 
 