package com.example.carrentalmanager.service;

import com.example.carrentalmanager.entity.Role;
import com.example.carrentalmanager.entity.User;
import com.example.carrentalmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User registerUser(String username, String password, String email, String phone, String driverLicense) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.CLIENT);
        user.setEmail(email);
        user.setPhone(phone);
        user.setDriverLicense(driverLicense);
        return save(user);
    }
}