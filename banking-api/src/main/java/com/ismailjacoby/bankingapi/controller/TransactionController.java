package com.ismailjacoby.bankingapi.controller;

import com.ismailjacoby.bankingapi.dto.transaction.TransactionRequest;
import com.ismailjacoby.bankingapi.dto.transaction.TransactionResponse;
import com.ismailjacoby.bankingapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long accountId,
                                          @RequestBody @Valid TransactionRequest request) {
        transactionService.deposit(accountId, request.amount());

        return ResponseEntity.status(HttpStatus.OK).body("Deposit Successful");
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable Long accountId,
            @RequestBody @Valid TransactionRequest request
    ) {
        transactionService.withdraw(accountId, request.amount());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Withdrawal successful");
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @PathVariable Long accountId
    ) {
        List<TransactionResponse> transactions =
                transactionService.getAccountTransactions(accountId);

        return ResponseEntity.ok(transactions);
    }
}
