package com.ismailjacoby.bankingapi.dto.account;

import com.ismailjacoby.bankingapi.models.enums.AccountStatus;
import com.ismailjacoby.bankingapi.models.enums.AccountType;
import com.ismailjacoby.bankingapi.models.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountNumber,
        BigDecimal balance,
        AccountStatus status,
        AccountType accountType,
        Currency currency,
        LocalDateTime createdAt
) {
}
