package com.ismailjacoby.bankingapi.service;

import com.ismailjacoby.bankingapi.dto.transaction.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void deposit(Long accountId, BigDecimal amount);
    void withdraw(Long accountId, BigDecimal amount);
    List<TransactionResponse> getAccountTransactions(Long accountId);
}
