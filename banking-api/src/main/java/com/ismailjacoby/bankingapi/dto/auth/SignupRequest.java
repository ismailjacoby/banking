package com.ismailjacoby.bankingapi.dto.auth;

import com.ismailjacoby.bankingapi.models.entity.Account;
import com.ismailjacoby.bankingapi.models.entity.Address;
import com.ismailjacoby.bankingapi.models.enums.Gender;
import com.ismailjacoby.bankingapi.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record SignupRequest(
        @NotBlank(message = "First name is required.")
        String firstName,

        @NotBlank(message = "Last name is required.")
        String lastName,

        @NotNull(message = "Date of Birth is required.")
        @Past(message = "Date of birth must be in the past.")
        LocalDate dateOfBirth,

        @NotNull(message = "Gender is required.")
        Gender gender,

        @NotBlank(message = "Nationality is required.")
        String nationality,

        @NotBlank(message = "Email is required.")
        @Email(message = "Please enter a valid email address.")
        String email,

        @NotBlank(message = "Phone number is required.")
        String phoneNumber,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        String password,

        @Valid
        @NotNull(message = "Address is required.")
        AddressRequest address
){}
