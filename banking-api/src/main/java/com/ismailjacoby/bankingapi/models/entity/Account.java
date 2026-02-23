package com.ismailjacoby.bankingapi.models.entity;

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
}
