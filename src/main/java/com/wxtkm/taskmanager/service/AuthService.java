package com.wxtkm.taskmanager.service;

import com.wxtkm.taskmanager.dto.LoginRequestDTO;
import com.wxtkm.taskmanager.dto.LoginResponseDTO;
import com.wxtkm.taskmanager.model.User;
import com.wxtkm.taskmanager.repository.UserRepository;
import com.wxtkm.taskmanager.service.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponseDTO(token);
    }
}