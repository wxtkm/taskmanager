package com.wxtkm.taskmanager.service;

import com.wxtkm.taskmanager.dto.UserRequestDTO;
import com.wxtkm.taskmanager.dto.UserResponseDTO;
import com.wxtkm.taskmanager.model.User;
import com.wxtkm.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO register(UserRequestDTO dto) {

        Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole("ROLE_USER");

        User saved = userRepository.save(user);

        return new UserResponseDTO(saved.getId(), saved.getUsername());
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getUsername()))
                .toList();
    }
}