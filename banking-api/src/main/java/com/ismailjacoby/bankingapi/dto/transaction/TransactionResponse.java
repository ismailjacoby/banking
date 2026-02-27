package com.ismailjacoby.bankingapi.dto.transaction;
import com.ismailjacoby.bankingapi.models.enums.Currency;
import com.ismailjacoby.bankingapi.models.enums.TransactionStatus;
import com.ismailjacoby.bankingapi.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        BigDecimal amount,
        Currency currency,
        TransactionType transactionType,
        TransactionStatus transactionStatus,
        LocalDateTime createdAt
) {

}
