package com.auditsphere.auditspherebackend.repository;

import com.auditsphere.auditspherebackend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByInvoiceNumber(String invoiceNumber);
    long countByRiskLevel(String riskLevel);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t")
    Double getTotalAmount();
    @Query("""
SELECT t.category, COUNT(t)
FROM Transaction t
GROUP BY t.category
""")
    List<Object[]> getTransactionCountByCategory();
    @Query("""
SELECT MONTH(t.transactionDate), COUNT(t)
FROM Transaction t
GROUP BY MONTH(t.transactionDate)
ORDER BY MONTH(t.transactionDate)
""")
    List<Object[]> getMonthlyTransactionSummary();
    @Query("""
SELECT t.riskLevel, COUNT(t)
FROM Transaction t
GROUP BY t.riskLevel
ORDER BY t.riskLevel
""")
    List<Object[]> getRiskSummary();
    @Query("""
SELECT t.vendorName, SUM(t.amount)
FROM Transaction t
GROUP BY t.vendorName
ORDER BY SUM(t.amount) DESC
""")
    List<Object[]> getTopVendors();
    @Query("""
SELECT t
FROM Transaction t
ORDER BY t.transactionDate DESC, t.id DESC
""")
    List<Transaction> findRecentTransactions();
    @Query("""
SELECT t
FROM Transaction t
WHERE t.riskLevel = 'HIGH'
ORDER BY t.amount DESC
""")
    List<Transaction> findHighRiskTransactions();
    long countByVendorName(String vendorName);
    List<Transaction> findByRiskLevel(String riskLevel);
    @Query("""
SELECT t.invoiceNumber, COUNT(t)
FROM Transaction t
GROUP BY t.invoiceNumber
HAVING COUNT(t) > 1
""")
    List<Object[]> findDuplicateInvoiceNumbers();
}