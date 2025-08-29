package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.LoginRequest;
import com.bookmyshow.main.dto.RegisterRequest;
import com.bookmyshow.main.exception.InvalidCredentialsException;
import com.bookmyshow.main.exception.ResourceAlreadyExistsException;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.RoleRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.security.JwtService;
import com.bookmyshow.main.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public String login(LoginRequest req) {
        try {
            //  Authenticate credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            //  Fetch user to extract role
            UserMaster user = userRepository.findByUsername(req.getUsername());
            if (user == null || user.getRole() == null) {
                throw new UserNotFoundException("User or role not found for username: " + req.getUsername());
            }

            String role = user.getRole().getRoleName().name(); // e.g. ADMIN, USER

            //  Generate token with role and return as string
            return jwtService.generateToken(req.getUsername(), role);  // Return the JWT token as a string
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Invalid credentials for username: " + req.getUsername());
        }
    }

    @Override
    public String register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already taken: " + req.getUsername());
        }

        UserMaster user = new UserMaster();
        user.setName(req.getName());
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());

        // Fetch and assign role
        Role role = roleRepository.findByRoleName(req.getRoleName())
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + req.getRoleName()));
        user.setRole(role);

        userRepository.save(user);

        return "User registered successfully";
    }
}
