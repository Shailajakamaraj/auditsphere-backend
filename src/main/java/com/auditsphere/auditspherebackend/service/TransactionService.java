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

    private final AuditLogService auditLogService;




    public TransactionService(
            TransactionRepository transactionRepository,
            RiskAnalysisService riskAnalysisService,
            AuditLogService auditLogService
    ) {

        this.transactionRepository = transactionRepository;

        this.riskAnalysisService = riskAnalysisService;

        this.auditLogService = auditLogService;

    }






    // CREATE TRANSACTION

    public TransactionResponseDTO createTransaction(
            TransactionRequestDTO dto
    ) {


        Transaction transaction = new Transaction();



        transaction.setInvoiceNumber(
                dto.getInvoiceNumber()
        );


        transaction.setVendorName(
                dto.getVendorName()
        );


        transaction.setAmount(
                dto.getAmount()
        );


        transaction.setTransactionDate(
                dto.getTransactionDate()
        );


        transaction.setCategory(
                dto.getCategory()
        );


        transaction.setStatus(
                dto.getStatus()
        );





        // Risk Analysis Engine

        riskAnalysisService.analyzeRisk(
                transaction
        );





        Transaction savedTransaction =
                transactionRepository.save(transaction);






        // Audit Log

        auditLogService.logAction(
                "CREATE_TRANSACTION",
                "Transaction",
                savedTransaction.getId()
        );





        return convertToDTO(
                savedTransaction
        );

    }









    // GET ALL TRANSACTIONS


    public List<TransactionResponseDTO> getAllTransactions() {


        return transactionRepository
                .findAll()

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }









    // GET TRANSACTION BY ID


    public TransactionResponseDTO getTransactionById(
            Long id
    ) {


        Transaction transaction =
                transactionRepository
                        .findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found"
                                )
                        );



        return convertToDTO(transaction);

    }









    // UPDATE TRANSACTION


    public TransactionResponseDTO updateTransaction(
            Long id,
            TransactionRequestDTO dto
    ) {



        Transaction transaction =
                transactionRepository
                        .findById(id)

                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found"
                                )
                        );





        transaction.setInvoiceNumber(
                dto.getInvoiceNumber()
        );


        transaction.setVendorName(
                dto.getVendorName()
        );


        transaction.setAmount(
                dto.getAmount()
        );


        transaction.setTransactionDate(
                dto.getTransactionDate()
        );


        transaction.setCategory(
                dto.getCategory()
        );


        transaction.setStatus(
                dto.getStatus()
        );






        // Recalculate Risk

        riskAnalysisService.analyzeRisk(
                transaction
        );





        Transaction updatedTransaction =
                transactionRepository.save(
                        transaction
                );







        auditLogService.logAction(
                "UPDATE_TRANSACTION",
                "Transaction",
                id
        );





        return convertToDTO(
                updatedTransaction
        );

    }









    // DELETE TRANSACTION


    public void deleteTransaction(
            Long id
    ) {



        if(!transactionRepository.existsById(id)) {


            throw new ResourceNotFoundException(
                    "Transaction not found"
            );

        }





        transactionRepository.deleteById(id);





        auditLogService.logAction(
                "DELETE_TRANSACTION",
                "Transaction",
                id
        );


    }









    // OPTIMIZED DUPLICATE TRANSACTIONS


    public List<TransactionResponseDTO> getDuplicateTransactions() {



        return transactionRepository
                .findDuplicateTransactions()

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }









    // HIGH RISK TRANSACTIONS


    public List<TransactionResponseDTO> getHighRiskTransactions() {


        return transactionRepository
                .findByRiskLevel("HIGH")

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }









    // DUPLICATE INVOICE NUMBERS


    public List<String> getDuplicateInvoiceNumbers(){



        return transactionRepository
                .findDuplicateTransactions()

                .stream()

                .map(Transaction::getInvoiceNumber)

                .distinct()

                .collect(Collectors.toList());

    }









    // ENTITY TO DTO


    private TransactionResponseDTO convertToDTO(
            Transaction transaction
    ) {



        TransactionResponseDTO dto =
                new TransactionResponseDTO();





        dto.setId(
                transaction.getId()
        );



        dto.setInvoiceNumber(
                transaction.getInvoiceNumber()
        );



        dto.setVendorName(
                transaction.getVendorName()
        );



        dto.setAmount(
                transaction.getAmount()
        );



        dto.setTransactionDate(
                transaction.getTransactionDate()
        );



        dto.setCategory(
                transaction.getCategory()
        );



        dto.setStatus(
                transaction.getStatus()
        );






        // Risk Information


        dto.setRiskLevel(
                transaction.getRiskLevel()
        );



        dto.setRiskScore(
                transaction.getRiskScore()
        );



        dto.setRiskReason(
                transaction.getRiskReason()
        );





        // Explainable AI


        dto.setRiskFactors(
                transaction.getRiskFactors()
        );



        dto.setAiRecommendation(
                transaction.getAiRecommendation()
        );





        return dto;

    }


}