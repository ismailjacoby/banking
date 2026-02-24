package com.ismailjacoby.bankingapi.controller;

import com.ismailjacoby.bankingapi.dto.auth.AuthDTO;
import com.ismailjacoby.bankingapi.dto.auth.LoginRequest;
import com.ismailjacoby.bankingapi.dto.auth.SignupRequest;
import com.ismailjacoby.bankingapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthDTO authDTO = authService.login(loginRequest);
        return ResponseEntity.ok(authDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
