package com.wxtkm.taskmanager.controller;

import com.wxtkm.taskmanager.dto.UserRequestDTO;
import com.wxtkm.taskmanager.dto.UserResponseDTO;
import com.wxtkm.taskmanager.model.User;
import com.wxtkm.taskmanager.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@Tag(name = "User Controller", description = "Registration and user management endpoints")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO dto) {

        Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());

        if (existingUser.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("User already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);

        return ResponseEntity.ok(
                new UserResponseDTO(saved.getId(), saved.getUsername())
        );
    }

    // 🔥 GET USERS (NO PASSWORD LEAK)
    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        List<UserResponseDTO> users = userRepository.findAll()
                .stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getUsername()))
                .toList();

        return ResponseEntity.ok(users);
    }
}