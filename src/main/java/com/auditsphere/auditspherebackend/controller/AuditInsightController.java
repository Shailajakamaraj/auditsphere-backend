package com.auditsphere.auditspherebackend.controller;

import com.auditsphere.auditspherebackend.entity.Transaction;
import com.auditsphere.auditspherebackend.repository.TransactionRepository;
import com.auditsphere.auditspherebackend.service.AuditInsightService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
public class AuditInsightController {


    private final AuditInsightService auditInsightService;
    private final TransactionRepository transactionRepository;


    public AuditInsightController(
            AuditInsightService auditInsightService,
            TransactionRepository transactionRepository) {

        this.auditInsightService = auditInsightService;
        this.transactionRepository = transactionRepository;
    }



    @GetMapping("/insight/{id}")
    public String generateInsight(@PathVariable Long id) {


        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Transaction not found"));


        return auditInsightService.generateInsight(transaction);
    }

}