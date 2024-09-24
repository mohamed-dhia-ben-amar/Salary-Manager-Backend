package com.dhaw.budgetmanagment.controllers;

import com.dhaw.budgetmanagment.models.LoginRequest;
import com.dhaw.budgetmanagment.models.User;
import com.dhaw.budgetmanagment.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = authService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword(), response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authService.logout(response);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }
}