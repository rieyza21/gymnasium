package com.project.gymnasium.service;


import com.project.gymnasium.model.AuthenticationResponse;
import com.project.gymnasium.model.Role;
import com.project.gymnasium.model.User;
import com.project.gymnasium.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;

import java.util.Optional;

import static org.aspectj.weaver.Shadow.ExceptionHandler;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(User request) {
        Optional<User> existingUser = repository.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            throw new IllegalStateException("User already exists");
        } else {
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user.setRole(Role.USER);

            user = repository.save(user);

            String token = jwtService.generateToken(Optional.of(user));

            return new AuthenticationResponse(token);
        }
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Optional<User> user = repository.findByUsername(request.getUsername());
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
