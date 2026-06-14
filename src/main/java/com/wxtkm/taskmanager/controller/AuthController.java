package com.wxtkm.taskmanager.controller;

import com.wxtkm.taskmanager.dto.*;
import com.wxtkm.taskmanager.model.User;
import com.wxtkm.taskmanager.repository.UserRepository;
import com.wxtkm.taskmanager.service.AuthService;
import com.wxtkm.taskmanager.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}