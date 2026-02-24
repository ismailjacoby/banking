package com.ismailjacoby.bankingapi.service.implementation;

import com.ismailjacoby.bankingapi.dto.auth.AuthDTO;
import com.ismailjacoby.bankingapi.dto.auth.LoginRequest;
import com.ismailjacoby.bankingapi.dto.auth.SignupRequest;
import com.ismailjacoby.bankingapi.exceptions.DuplicateException;
import com.ismailjacoby.bankingapi.exceptions.NotFoundException;
import com.ismailjacoby.bankingapi.models.entity.Address;
import com.ismailjacoby.bankingapi.models.entity.User;
import com.ismailjacoby.bankingapi.models.enums.UserRole;
import com.ismailjacoby.bankingapi.repository.UserRepository;
import com.ismailjacoby.bankingapi.security.JWTProvider;
import com.ismailjacoby.bankingapi.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthDTO login(LoginRequest loginRequest) {

        String email = loginRequest.email().trim().toLowerCase();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequest.password()));
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid email or password.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));

        String token = jwtProvider.generateToken(user.getEmail(), user.getRole());

        return AuthDTO.builder()
                .token(token)
                .username(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        String email = signupRequest.email().toLowerCase().trim();
        String password = passwordEncoder.encode(signupRequest.password());

        if (userRepository.existsByEmail(email)){
            throw new DuplicateException("User with this email already exists");
        }

        Address address = new Address();
        address.setStreet(signupRequest.address().street());
        address.setZipCode(signupRequest.address().zipCode());
        address.setCity(signupRequest.address().city());
        address.setCountry(signupRequest.address().country());


        User user = new User();
        user.setFirstName(signupRequest.firstName().trim());
        user.setLastName(signupRequest.lastName().trim());
        user.setDateOfBirth(signupRequest.dateOfBirth());
        user.setGender(signupRequest.gender());
        user.setNationality(signupRequest.nationality().trim());
        user.setEmail(email);
        user.setPhoneNumber(signupRequest.phoneNumber().trim());
        user.setPassword(password);
        user.setAddress(address);
        user.setRole(UserRole.CUSTOMER);
        user.setEnabled(true);
        user.setFailedLoginAttempts(0);
        user.setAccountLocked(false);
        userRepository.save(user);
    }
}
