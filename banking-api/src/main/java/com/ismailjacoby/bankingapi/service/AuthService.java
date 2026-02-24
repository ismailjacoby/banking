package com.ismailjacoby.bankingapi.service;

import com.ismailjacoby.bankingapi.dto.auth.AuthDTO;
import com.ismailjacoby.bankingapi.dto.auth.LoginRequest;
import com.ismailjacoby.bankingapi.dto.auth.SignupRequest;

public interface AuthService {
    AuthDTO login(LoginRequest loginRequest);
    void signup(SignupRequest signupRequest);
}
