package com.ismailjacoby.bankingapi.models.entity;

import com.ismailjacoby.bankingapi.exceptions.InsufficientBalanceException;
import com.ismailjacoby.bankingapi.models.enums.AccountStatus;
import com.ismailjacoby.bankingapi.models.enums.AccountType;
import com.ismailjacoby.bankingapi.models.enums.Currency;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Column(nullable = false, unique = true, updatable = false)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    protected Account() {
    }

    public Account(String accountNumber,
                   User user,
                   AccountType accountType,
                   Currency currency) {
        this.accountNumber = accountNumber;
        this.user = user;
        this.accountType = accountType;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
    }

    public void deposit(BigDecimal amount) {
        ensureAccountActive();
        validatePositive(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        ensureAccountActive();
        validatePositive(amount);

        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        this.balance = this.balance.subtract(amount);
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

    public void lock() {
        this.status = AccountStatus.BLOCKED;
    }

    public void close() {
        this.status = AccountStatus.CLOSED;
    }

    private void ensureAccountActive() {
        if (this.status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active.");
        }
    }

    private void validatePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

}
