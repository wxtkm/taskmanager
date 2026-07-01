package com.wxtkm.taskmanager.controller;

import com.wxtkm.taskmanager.dto.AdminLoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${ADMIN_TOKEN}")
    private String adminToken;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {

        if (request.getPassword() == null ||
                !request.getPassword().equals(adminPassword)) {
            return ResponseEntity.status(401).body("Wrong password");
        }

        return ResponseEntity.ok(Map.of(
                "token", adminToken
        ));
    }
}