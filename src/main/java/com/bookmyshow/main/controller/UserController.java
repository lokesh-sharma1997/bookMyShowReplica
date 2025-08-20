package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //  Constructor Injection (no @Autowired on final field)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        Optional<UserDTO> user = userService.getByUserId(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Get All Users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔹 Delete User by ID (keeping PATCH as you wanted)
   
    @PatchMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(404).body("User not found");
    }

    // 🔹 Search by Name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<UserDTO>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByName(name));
    }

    // 🔹 Search by Username
    @GetMapping("/search/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        Optional<UserDTO> user = userService.getByUsername(username);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }


 // 🔹 Search by Role
    @GetMapping("/search/role/{roleName}")
    public ResponseEntity<?> getByRole(@PathVariable String roleName) {
        try {
            List<UserDTO> users = userService.getByRole(roleName);
            if (users.isEmpty()) {
                return ResponseEntity.status(404).body("No users found with role: " + roleName);
            }
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role name: " + roleName);
        }
    }


    // 🔹 Search by Phone Number
    @GetMapping("/search/phone/{phone}")
    public ResponseEntity<?> getByPhone(@PathVariable long phone) {
        Optional<UserDTO> user = userService.getByPhoneNumber(phone);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
