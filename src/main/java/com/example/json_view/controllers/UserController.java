package com.example.json_view.controllers;

import com.example.json_view.dto.Views;
import com.example.json_view.model.User;
import com.example.json_view.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;


    @GetMapping
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/{userId}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new));
    }

    @PostMapping
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @PutMapping("/{userId}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        return ResponseEntity.ok().body(userRepository.save(existingUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
