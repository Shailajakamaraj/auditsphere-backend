package com.auditsphere.auditspherebackend.repository;


import com.auditsphere.auditspherebackend.entity.RiskLevel;
import com.auditsphere.auditspherebackend.entity.Transaction;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;



public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {



    List<Transaction> findByInvoiceNumber(
            String invoiceNumber
    );



    long countByVendorName(
            String vendorName
    );



    List<Transaction> findByRiskLevel(
            RiskLevel riskLevel
    );



    long countByRiskLevel(
            RiskLevel riskLevel
    );




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





    // Monthly Transaction Summary

    @Query("""
            SELECT
            YEAR(t.transactionDate),
            MONTH(t.transactionDate),
            COUNT(t)
            FROM Transaction t
            WHERE t.transactionDate IS NOT NULL
            GROUP BY YEAR(t.transactionDate),
                     MONTH(t.transactionDate)
            ORDER BY YEAR(t.transactionDate),
                     MONTH(t.transactionDate)
            """)
    List<Object[]> getMonthlySummary();





    // Risk Distribution

    @Query("""
            SELECT
            t.riskLevel,
            COUNT(t)
            FROM Transaction t
            GROUP BY t.riskLevel
            """)
    List<Object[]> getRiskSummary();



}