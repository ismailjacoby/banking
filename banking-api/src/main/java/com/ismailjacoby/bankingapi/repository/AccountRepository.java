package com.ismailjacoby.bankingapi.repository;

import com.ismailjacoby.bankingapi.dto.account.AccountResponse;
import com.ismailjacoby.bankingapi.models.entity.Account;
import com.ismailjacoby.bankingapi.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);
    List<Account> findByUser(User user);
    AccountResponse getAccountsByUser(User user);
}
