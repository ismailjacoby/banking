package com.ismailjacoby.bankingapi.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
    @NotBlank(message = "Street is required.")
    String street,

    @NotBlank(message = "Zip code is required.")
    String zipCode,

    @NotBlank(message = "City is required.")
    String city,

    @NotBlank(message = "Country is required.")
    String country
){}
