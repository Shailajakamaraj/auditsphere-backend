package com.auditsphere.auditspherebackend.service;

import com.auditsphere.auditspherebackend.entity.Transaction;
import com.auditsphere.auditspherebackend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAnalysisService {

    private final TransactionRepository transactionRepository;


    public RiskAnalysisService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public void analyzeRisk(Transaction transaction) {

        System.out.println("🔥 RISK ENGINE STARTED");


        int score = 0;
        StringBuilder reason = new StringBuilder();


        // Rule 1: High amount check
        if (transaction.getAmount() > 50000) {

            score += 20;
            reason.append("High transaction amount. ");
        }


        // Rule 2: Duplicate invoice check
        List<Transaction> existingTransactions =
                transactionRepository.findByInvoiceNumber(
                        transaction.getInvoiceNumber()
                );


        if (!existingTransactions.isEmpty()) {

            score += 40;
            reason.append("Duplicate invoice detected. ");
        }



        // Rule 3: New vendor check
        long vendorCount =
                transactionRepository.countByVendorName(
                        transaction.getVendorName()
                );


        if (vendorCount == 0) {

            score += 15;
            reason.append("New vendor detected. ");
        }



        System.out.println("FINAL SCORE = " + score);
        System.out.println("REASON = " + reason);



        // Risk classification
        if (score >= 60) {

            transaction.setRiskLevel("HIGH");

        } else if (score >= 30) {

            transaction.setRiskLevel("MEDIUM");

        } else {

            transaction.setRiskLevel("LOW");
        }



        transaction.setRiskScore(score);
        transaction.setRiskReason(reason.toString());

    }
}