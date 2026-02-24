package com.ismailjacoby.bankingapi.controller;

import com.ismailjacoby.bankingapi.dto.account.AccountCreationRequest;
import com.ismailjacoby.bankingapi.dto.account.AccountResponse;
import com.ismailjacoby.bankingapi.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountCreationRequest accountCreationRequest){
        AccountResponse response = accountService.createAccount(accountCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(){
        List<AccountResponse> accounts = accountService.getUserAccounts();
        return ResponseEntity.ok(accounts);
    }
}
