package com.ismailjacoby.bankingapi.service;

import com.ismailjacoby.bankingapi.dto.account.AccountCreationRequest;
import com.ismailjacoby.bankingapi.dto.account.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountCreationRequest request);
    List<AccountResponse> getUserAccounts();
}
