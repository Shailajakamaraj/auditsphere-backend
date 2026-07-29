package com.auditsphere.auditspherebackend.service;

import com.auditsphere.auditspherebackend.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class AuditInsightService {


    private final GeminiService geminiService;


    public AuditInsightService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }



    public String generateInsight(Transaction transaction) {


        String prompt =
                """
                You are an internal auditor.

                Analyze this transaction:

                Vendor: %s
                Amount: %s
                Risk Level: %s
                Risk Score: %s
                Reason: %s

                Provide:
                1. Risk explanation
                2. Possible fraud concern
                3. Recommended auditor action
                """
                        .formatted(
                                transaction.getVendorName(),
                                transaction.getAmount(),
                                transaction.getRiskLevel(),
                                transaction.getRiskScore(),
                                transaction.getRiskReason()
                        );


        return geminiService.generateInsight(prompt);
    }
}