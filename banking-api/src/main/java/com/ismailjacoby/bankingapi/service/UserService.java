package com.ismailjacoby.bankingapi.service;

import com.ismailjacoby.bankingapi.dto.auth.SignupRequest;

public interface UserService {
    void signup(SignupRequest signupRequest);
}
