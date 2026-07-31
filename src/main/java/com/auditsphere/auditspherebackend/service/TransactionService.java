package com.auditsphere.auditspherebackend.service;


import com.auditsphere.auditspherebackend.dto.TransactionDetailsDTO;
import com.auditsphere.auditspherebackend.dto.TransactionRequestDTO;
import com.auditsphere.auditspherebackend.dto.TransactionResponseDTO;


import com.auditsphere.auditspherebackend.entity.RiskLevel;
import com.auditsphere.auditspherebackend.entity.Transaction;


import com.auditsphere.auditspherebackend.exception.ResourceNotFoundException;

import com.auditsphere.auditspherebackend.repository.TransactionRepository;


import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class TransactionService {



    private final TransactionRepository transactionRepository;


    private final RiskAnalysisService riskAnalysisService;


    private final AuditLogService auditLogService;




    public TransactionService(
            TransactionRepository transactionRepository,
            RiskAnalysisService riskAnalysisService,
            AuditLogService auditLogService
    ){

        this.transactionRepository = transactionRepository;
        this.riskAnalysisService = riskAnalysisService;
        this.auditLogService = auditLogService;

    }






    // CREATE TRANSACTION

    public TransactionResponseDTO createTransaction(
            TransactionRequestDTO dto
    ){


        Transaction transaction =
                new Transaction();



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




        riskAnalysisService.analyzeRisk(
                transaction
        );



        Transaction saved =
                transactionRepository.save(
                        transaction
                );



        auditLogService.logAction(
                "CREATE_TRANSACTION",
                "Transaction",
                saved.getId()
        );



        return convertToDTO(saved);

    }









    // GET ALL

    public List<TransactionResponseDTO> getAllTransactions(){


        return transactionRepository
                .findAll()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }









    // GET BY ID

    public TransactionResponseDTO getTransactionById(
            Long id
    ){


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









    // UPDATE

    public TransactionResponseDTO updateTransaction(
            Long id,
            TransactionRequestDTO dto
    ){


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




        riskAnalysisService.analyzeRisk(
                transaction
        );



        Transaction updated =
                transactionRepository.save(
                        transaction
                );



        auditLogService.logAction(
                "UPDATE_TRANSACTION",
                "Transaction",
                id
        );



        return convertToDTO(updated);

    }









    // DELETE

    public void deleteTransaction(
            Long id
    ){


        if(!transactionRepository.existsById(id)){

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









    // DUPLICATES

    public List<TransactionResponseDTO> getDuplicateTransactions(){


        return transactionRepository
                .findDuplicateTransactions()

                .stream()

                .map(this::convertToDTO)

                .toList();

    }

    public TransactionDetailsDTO getTransactionDetails(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        return TransactionDetailsDTO.builder()

                .id(transaction.getId())

                .invoiceNumber(transaction.getInvoiceNumber())

                .vendorName(transaction.getVendorName())

                .amount(transaction.getAmount())

                .transactionDate(transaction.getTransactionDate())

                .category(transaction.getCategory())

                .status(transaction.getStatus().name())

                .riskLevel(
                        transaction.getRiskLevel() == null
                                ? "LOW"
                                : transaction.getRiskLevel().name()
                )

                .riskScore(
                        transaction.getRiskScore() == null
                                ? 0
                                : transaction.getRiskScore()
                )

                .riskReason(
                        transaction.getRiskReason() == null
                                ? "No risk detected."
                                : transaction.getRiskReason()
                )

                .riskFactors(
                        transaction.getRiskFactors() == null
                                ? "No risk factors available."
                                : transaction.getRiskFactors()
                )

                .aiRecommendation(
                        transaction.getAiRecommendation() == null
                                ? "AI recommendation not available."
                                : transaction.getAiRecommendation()
                )

                .build();
    }






    // HIGH RISK

    public List<TransactionResponseDTO> getHighRiskTransactions(){


        return transactionRepository
                .findByRiskLevel(
                        RiskLevel.HIGH
                )

                .stream()

                .map(this::convertToDTO)

                .toList();

    }









    // DUPLICATE INVOICE NUMBERS

    public List<String> getDuplicateInvoiceNumbers(){


        return transactionRepository
                .findDuplicateTransactions()

                .stream()

                .map(Transaction::getInvoiceNumber)

                .distinct()

                .toList();

    }









    // ENTITY -> DTO

    private TransactionResponseDTO convertToDTO(
            Transaction transaction
    ){


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

                transaction.getStatus()!=null
                        ?
                        transaction.getStatus().name()
                        :
                        null

        );



        dto.setRiskLevel(

                transaction.getRiskLevel()!=null
                        ?
                        transaction.getRiskLevel().name()
                        :
                        null

        );



        dto.setRiskScore(
                transaction.getRiskScore()
        );


        dto.setRiskReason(
                transaction.getRiskReason()
        );


        dto.setRiskFactors(
                transaction.getRiskFactors()
        );


        dto.setAiRecommendation(
                transaction.getAiRecommendation()
        );



        return dto;

    }



}