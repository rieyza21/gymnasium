package com.project.gymnasium.service;


import com.project.gymnasium.model.User;
import com.project.gymnasium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getUser() {
        return repository.findAll();
    }

    public User updateUserDetails(int id, String phoneNumber, String address) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        return repository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return repository.findById(id);
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    public void deleteAllUsers() {
        repository.deleteAll();
    }
}
