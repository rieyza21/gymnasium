package com.project.gymnasium.controller;

import com.project.gymnasium.model.User;
import com.project.gymnasium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

        @Autowired
        private UserService userService;

        @GetMapping("/get")
        public List<User> getAllUser() {
            return userService.getUser();
        }

        @GetMapping("/get/{id}")
        public Optional<User> getUserById(@PathVariable int id) {
            return userService.getUserById(id);
        }

        @DeleteMapping("/delete/{id}")
        public void deleteUser(@PathVariable int id) {
            userService.deleteUser(id);
        }

        @DeleteMapping("/delete")
        public void deleteAllUser() {
            userService.deleteAllUsers();
        }

}
