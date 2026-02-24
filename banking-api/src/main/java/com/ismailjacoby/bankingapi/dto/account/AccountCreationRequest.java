package com.ismailjacoby.bankingapi.dto.account;

import com.ismailjacoby.bankingapi.models.enums.AccountType;
import com.ismailjacoby.bankingapi.models.enums.Currency;
import jakarta.validation.constraints.NotNull;

public record AccountCreationRequest (
    @NotNull(message = "Account type is required.")
    AccountType accountType,

    @NotNull(message = "Currency is required.")
    Currency currency
){
}
