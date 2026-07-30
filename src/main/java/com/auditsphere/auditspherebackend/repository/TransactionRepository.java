package com.auditsphere.auditspherebackend.repository;


import com.auditsphere.auditspherebackend.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {



    // Find duplicate invoice checking
    List<Transaction> findByInvoiceNumber(
            String invoiceNumber
    );



    // New vendor detection
    long countByVendorName(
            String vendorName
    );



    // High risk transactions
    List<Transaction> findByRiskLevel(
            String riskLevel
    );



    // Optimized duplicate transaction query
    @Query("""
            SELECT t
            FROM Transaction t
            WHERE t.invoiceNumber IN
            (
                SELECT t2.invoiceNumber
                FROM Transaction t2
                GROUP BY t2.invoiceNumber
                HAVING COUNT(t2.invoiceNumber) > 1
            )
            """)
    List<Transaction> findDuplicateTransactions();



    // Dashboard statistics

    long countByRiskLevel(
            String riskLevel
    );


}