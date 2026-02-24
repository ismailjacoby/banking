package com.ismailjacoby.bankingapi.service.implementation;

import com.ismailjacoby.bankingapi.dto.account.AccountCreationRequest;
import com.ismailjacoby.bankingapi.dto.account.AccountResponse;
import com.ismailjacoby.bankingapi.exceptions.NotFoundException;
import com.ismailjacoby.bankingapi.models.entity.Account;
import com.ismailjacoby.bankingapi.models.entity.User;
import com.ismailjacoby.bankingapi.repository.AccountRepository;
import com.ismailjacoby.bankingapi.repository.UserRepository;
import com.ismailjacoby.bankingapi.service.AccountService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountResponse createAccount(AccountCreationRequest request) {
        User user = getAuthenticatedUser();

        String accountNumber;

        do {
            accountNumber = generateAccountNumber();
        } while (accountRepository.existsByAccountNumber(accountNumber));


        Account account = new Account(
                accountNumber,
                user,
                request.accountType(),
                request.currency()
        );

        accountRepository.save(account);

        return mapToResponse(account);
    }

    @Override
    public List<AccountResponse> getUserAccounts() {
        User user = getAuthenticatedUser();

        return accountRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private AccountResponse mapToResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus(),
                account.getAccountType(),
                account.getCurrency(),
                account.getCreatedAt()
        );
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Unauthorized");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
