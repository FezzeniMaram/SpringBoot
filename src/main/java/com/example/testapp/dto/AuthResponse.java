package com.example.testapp.dto;

import java.time.LocalDate;

public class AuthResponse {
    private boolean success;
    private String message;
    private String token;
    private String role;
    private String username;
    private Long id;
    private String gender;
    private LocalDate dateNaissance;
    private String email;

    public AuthResponse(boolean success, String message, String token, String role, String username, Long id,
                        String gender, LocalDate dateNaissance,  String email) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.role = role;
        this.username = username;
        this.id = id;
        this.gender = gender;
        this.dateNaissance = dateNaissance;
        this.email=email;
    }

    // Getters & Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
