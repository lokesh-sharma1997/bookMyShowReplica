package com.bookmyshow.main.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.User;
import com.bookmyshow.main.repository.RoleRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Convert Entity -> DTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName().name() : null);
        dto.setCreatedOn(user.getCreatedOn());
        dto.setUpdatedOn(user.getUpdatedOn());
        dto.setDeleteFlag(user.getDeleteFlag());
        return dto;
    }

    // ✅ Convert DTO -> Entity
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getRoleName() != null) {
            try {
                Role.RoleName roleEnum = Role.RoleName.valueOf(dto.getRoleName().toUpperCase());
                Optional<Role> roleOpt = roleRepository.findByRoleName(roleEnum);
                roleOpt.ifPresent(user::setRole);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role: " + dto.getRoleName());
            }
        }


        user.setCreatedOn(dto.getCreatedOn());
        user.setUpdatedOn(dto.getUpdatedOn());
        user.setDeleteFlag(dto.getDeleteFlag() != null ? dto.getDeleteFlag() : false);

        return user;
    }

    @Override
    public Optional<UserDTO> getByUserId(int userId) {
        return Optional.ofNullable(userRepository.findByUserId(userId))
                       .map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> getByName(String name) {
        return userRepository.findByName(name)
                             .stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                       .map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> getByEmail(String email) {
    	return userRepository.findByEmailIgnoreCase(email)
                .map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> getByRole(String roleName) {
        try {
            // Convert incoming String to Enum
            Role.RoleName roleEnum = Role.RoleName.valueOf(roleName.toUpperCase());

            Optional<Role> roleOpt = roleRepository.findByRoleName(roleEnum);
            if (roleOpt.isEmpty()) {
                return List.of();
            }

            return userRepository.findByRole(roleOpt.get())
                                 .stream()
                                 .map(this::convertToDTO)
                                 .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + roleName);
        }
    }



    @Override
    public Optional<UserDTO> getByPhoneNumber(long phoneNumber) {
        return Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber))
                       .map(this::convertToDTO);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }


    @Override
    public boolean deleteById(int userId) {
        return userRepository.findById(userId).map(user -> {
            // swap the deleteFlag value (true -> false, false -> true)
            user.setDeleteFlag(user.getDeleteFlag() == null ? true : !user.getDeleteFlag());
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

}
