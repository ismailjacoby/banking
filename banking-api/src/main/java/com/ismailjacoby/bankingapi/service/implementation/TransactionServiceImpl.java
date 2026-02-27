package com.ismailjacoby.bankingapi.service.implementation;

import com.ismailjacoby.bankingapi.dto.transaction.TransactionResponse;
import com.ismailjacoby.bankingapi.exceptions.NotFoundException;
import com.ismailjacoby.bankingapi.models.entity.Account;
import com.ismailjacoby.bankingapi.models.entity.Transaction;
import com.ismailjacoby.bankingapi.models.entity.User;
import com.ismailjacoby.bankingapi.models.enums.TransactionStatus;
import com.ismailjacoby.bankingapi.models.enums.TransactionType;
import com.ismailjacoby.bankingapi.repository.AccountRepository;
import com.ismailjacoby.bankingapi.repository.TransactionRepository;
import com.ismailjacoby.bankingapi.repository.UserRepository;
import com.ismailjacoby.bankingapi.service.TransactionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        User user = getAuthenticatedUser();
        Account account = getOwnedAccount(accountId, user);

        account.deposit(amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCurrency(account.getCurrency());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setDestinationAccount(account);

        transactionRepository.save(transaction);


    }

    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        User user = getAuthenticatedUser();
        Account account = getOwnedAccount(accountId, user);

        account.withdraw(amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCurrency(account.getCurrency());
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setSourceAccount(account);

        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionResponse> getAccountTransactions(Long accountId) {
        User user = getAuthenticatedUser();
        Account account = getOwnedAccount(accountId, user);

        return transactionRepository.findBySourceAccountIdOrDestinationAccountIdOrderByCreatedAtDesc(
                account.getId(),
                account.getId()
        ).stream()
                .map(this::mapToResponse)
                .toList();
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

    private Account getOwnedAccount(Long accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this account");
        }

        return account;
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getTransactionType(),
                transaction.getTransactionStatus(),
                transaction.getCreatedAt()
        );
    }
}
