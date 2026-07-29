package com.auditsphere.auditspherebackend.controller;

import com.auditsphere.auditspherebackend.dto.TransactionRequestDTO;
import com.auditsphere.auditspherebackend.dto.TransactionResponseDTO;
import com.auditsphere.auditspherebackend.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    // Create Transaction
    @PostMapping
    public TransactionResponseDTO createTransaction(
            @RequestBody TransactionRequestDTO dto) {

        return transactionService.createTransaction(dto);
    }


    // Get All Transactions
    @GetMapping
    public List<TransactionResponseDTO> getAllTransactions() {

        return transactionService.getAllTransactions();
    }


    // Get Transaction By ID
    @GetMapping("/{id}")
    public TransactionResponseDTO getTransactionById(
            @PathVariable Long id) {

        return transactionService.getTransactionById(id);
    }


    // Duplicate Transactions
    @GetMapping("/duplicates")
    public List<TransactionResponseDTO> getDuplicates() {

        return transactionService.getDuplicateTransactions();
    }


    // High Risk Transactions
    @GetMapping("/high-risk")
    public List<TransactionResponseDTO> getHighRiskTransactions() {

        return transactionService.getHighRiskTransactions();
    }
}