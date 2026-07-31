package com.auditsphere.auditspherebackend.controller;

import com.auditsphere.auditspherebackend.dto.TransactionDetailsDTO;

import com.auditsphere.auditspherebackend.dto.TransactionRequestDTO;
import com.auditsphere.auditspherebackend.dto.TransactionResponseDTO;
import com.auditsphere.auditspherebackend.service.TransactionService;


import jakarta.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/transactions")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:5176"
        }
)
public class TransactionController {



    private final TransactionService transactionService;



    public TransactionController(
            TransactionService transactionService
    ){

        this.transactionService = transactionService;

    }





    // USER + MANAGER + ADMIN

    @PostMapping
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','USER')"
    )
    public TransactionResponseDTO createTransaction(
            @RequestBody @Valid TransactionRequestDTO dto
    ){

        return transactionService.createTransaction(dto);

    }







    // ADMIN + MANAGER + AUDITOR

    @GetMapping
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')"
    )
    public List<TransactionResponseDTO> getAllTransactions(){

        return transactionService.getAllTransactions();

    }







    // ADMIN + MANAGER + AUDITOR




    @GetMapping("/{id}")
    public ResponseEntity<TransactionDetailsDTO> getTransactionDetails(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                transactionService.getTransactionDetails(id)
        );
    }


    // DUPLICATE INVOICE ANALYSIS

    @GetMapping("/duplicates")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')"
    )
    public List<TransactionResponseDTO> getDuplicates(){

        return transactionService.getDuplicateTransactions();

    }







    // HIGH RISK ANALYSIS

    @GetMapping("/high-risk")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')"
    )
    public List<TransactionResponseDTO> getHighRiskTransactions(){

        return transactionService.getHighRiskTransactions();

    }







    // ADMIN + MANAGER

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER')"
    )
    public TransactionResponseDTO updateTransaction(
            @PathVariable Long id,
            @RequestBody @Valid TransactionRequestDTO dto
    ){

        return transactionService.updateTransaction(
                id,
                dto
        );

    }







    // ONLY ADMIN

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    public void deleteTransaction(
            @PathVariable Long id
    ){

        transactionService.deleteTransaction(id);

    }


}