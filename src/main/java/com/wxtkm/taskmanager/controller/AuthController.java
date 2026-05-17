package com.wxtkm.taskmanager.controller;

import com.wxtkm.taskmanager.dto.*;
import com.wxtkm.taskmanager.model.User;
import com.wxtkm.taskmanager.repository.UserRepository;
import com.wxtkm.taskmanager.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {

        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}