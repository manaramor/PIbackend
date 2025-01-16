package com.example.adbenturback.model;

public class LoginResponse {
    private String message;
    private String token;
    private Long userId;
    private String role;

    public LoginResponse(String message) {
        this.message = message;
    }

    public LoginResponse(String message, String token, Long userId, String role) {
        this.message = message;
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

    //Getters y setters
    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
