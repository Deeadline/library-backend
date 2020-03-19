package com.yoko.libraryproject.controller;

import com.yoko.libraryproject.dto.JwtResponse;
import com.yoko.libraryproject.dto.LoginDto;
import com.yoko.libraryproject.dto.RegisterDto;
import com.yoko.libraryproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces = "application/json", consumes = "application/json")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginRequest) {
        JwtResponse response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto signUpRequest) {
        return userService.register(signUpRequest);
    }
}