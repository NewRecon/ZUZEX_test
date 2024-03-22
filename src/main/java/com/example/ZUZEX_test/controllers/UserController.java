package com.example.ZUZEX_test.controllers;

import com.example.ZUZEX_test.models.User;
import com.example.ZUZEX_test.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.AccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UsernameNotFoundException{
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws RuntimeException, AccessException {
        userService.deleteUser(id);
    }
}
