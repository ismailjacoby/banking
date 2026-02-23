package com.ismailjacoby.bankingapi.service.implementation;

import com.ismailjacoby.bankingapi.dto.auth.SignupRequest;
import com.ismailjacoby.bankingapi.exceptions.DuplicateException;
import com.ismailjacoby.bankingapi.models.entity.Address;
import com.ismailjacoby.bankingapi.models.entity.User;
import com.ismailjacoby.bankingapi.models.enums.UserRole;
import com.ismailjacoby.bankingapi.repository.UserRepository;
import com.ismailjacoby.bankingapi.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
