package com.ismailjacoby.bankingapi.repository;

import com.ismailjacoby.bankingapi.models.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountIdOrDestinationAccountIdOrderByCreatedAtDesc(
            Long sourceAccountId,
            Long destinationAccountId);
}
