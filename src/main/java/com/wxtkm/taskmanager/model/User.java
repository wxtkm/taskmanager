package com.wxtkm.taskmanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Schema(description = "User entity representing a system user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique user ID", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Username used for login", example = "test1")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Encrypted password (BCrypt)", hidden = true)
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}