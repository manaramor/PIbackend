package com.example.adbenturback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adbenturback.model.LoginRequest;
import com.example.adbenturback.model.LoginResponse;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); 
        return ResponseEntity.ok(new LoginResponse("Logout successful"));
    }
}
