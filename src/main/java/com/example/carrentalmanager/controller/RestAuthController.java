package com.example.carrentalmanager.controller;

import com.example.carrentalmanager.entity.User;
import com.example.carrentalmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RestAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        
        // Check if user already exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            response.put("error", "Username already exists");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Register the user
        User registeredUser = userService.registerUser(
            user.getUsername(), 
            user.getPassword(), 
            user.getEmail(), 
            user.getPhone(), 
            user.getDriverLicense()
        );
        
        response.put("message", "User registered successfully");
        response.put("username", registeredUser.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        
        // In a real application, you would authenticate the user
        // For this simple app, we just check if user exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }
    }
}