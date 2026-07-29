package com.auditsphere.auditspherebackend.service;

import com.auditsphere.auditspherebackend.dto.TransactionRequestDTO;
import com.auditsphere.auditspherebackend.dto.TransactionResponseDTO;
import com.auditsphere.auditspherebackend.entity.Transaction;
import com.auditsphere.auditspherebackend.exception.ResourceNotFoundException;
import com.auditsphere.auditspherebackend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RiskAnalysisService riskAnalysisService;


    public TransactionService(
            TransactionRepository transactionRepository,
            RiskAnalysisService riskAnalysisService) {

        this.transactionRepository = transactionRepository;
        this.riskAnalysisService = riskAnalysisService;
    }


    // Create Transaction
    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {

        Transaction transaction = new Transaction();

        transaction.setInvoiceNumber(dto.getInvoiceNumber());
        transaction.setVendorName(dto.getVendorName());
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setCategory(dto.getCategory());
        transaction.setStatus(dto.getStatus());


        // AI-ready Risk Engine
        riskAnalysisService.analyzeRisk(transaction);


        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return convertToDTO(savedTransaction);
    }



    // Get All Transactions
    public List<TransactionResponseDTO> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    // Get Transaction By ID
    public TransactionResponseDTO getTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found"));

        return convertToDTO(transaction);
    }



    // Update Transaction
    public TransactionResponseDTO updateTransaction(
            Long id,
            TransactionRequestDTO dto) {


        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found"));


        transaction.setInvoiceNumber(dto.getInvoiceNumber());
        transaction.setVendorName(dto.getVendorName());
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setCategory(dto.getCategory());
        transaction.setStatus(dto.getStatus());


        // Recalculate risk after update
        riskAnalysisService.analyzeRisk(transaction);


        Transaction updatedTransaction =
                transactionRepository.save(transaction);


        return convertToDTO(updatedTransaction);
    }



    // Delete Transaction
    public void deleteTransaction(Long id) {

        transactionRepository.deleteById(id);
    }



    // Find Duplicate Transactions
    public List<TransactionResponseDTO> getDuplicateTransactions() {

        return transactionRepository.findAll()
                .stream()
                .filter(transaction ->
                        transactionRepository
                                .findByInvoiceNumber(
                                        transaction.getInvoiceNumber()
                                )
                                .size() > 1
                )
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    // Entity -> DTO Conversion
    private TransactionResponseDTO convertToDTO(
            Transaction transaction) {


        TransactionResponseDTO dto =
                new TransactionResponseDTO();


        dto.setId(transaction.getId());
        dto.setInvoiceNumber(transaction.getInvoiceNumber());
        dto.setVendorName(transaction.getVendorName());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setCategory(transaction.getCategory());
        dto.setStatus(transaction.getStatus());
        dto.setRiskLevel(transaction.getRiskLevel());
        dto.setRiskScore(transaction.getRiskScore());
        dto.setRiskReason(transaction.getRiskReason());

        return dto;
    }
    // Get High Risk Transactions
    public List<TransactionResponseDTO> getHighRiskTransactions() {

        return transactionRepository.findByRiskLevel("HIGH")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<String> getDuplicateInvoiceNumbers(){

        return transactionRepository.findDuplicateInvoiceNumbers()
                .stream()
                .map(row -> row[0].toString())
                .collect(Collectors.toList());
    }

}