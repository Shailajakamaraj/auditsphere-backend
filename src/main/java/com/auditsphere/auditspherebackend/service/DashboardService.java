package com.auditsphere.auditspherebackend.service;

import com.auditsphere.auditspherebackend.dto.DashboardResponseDTO;
import com.auditsphere.auditspherebackend.repository.TransactionRepository;
import org.springframework.stereotype.Service;


@Service
public class DashboardService {


    private final TransactionRepository transactionRepository;


    public DashboardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }



    public DashboardResponseDTO getDashboardData(){


        DashboardResponseDTO dto =
                new DashboardResponseDTO();


        dto.setTotalAmount(
                transactionRepository.getTotalAmount()
        );


        dto.setTotalTransactions(
                transactionRepository.count()
        );


        dto.setHighRiskCount(
                transactionRepository.countByRiskLevel("HIGH")
        );


        dto.setRiskSummary(
                transactionRepository.getRiskSummary()
        );


        dto.setCategorySummary(
                transactionRepository.getTransactionCountByCategory()
        );


        dto.setMonthlySummary(
                transactionRepository.getMonthlyTransactionSummary()
        );


        dto.setTopVendors(
                transactionRepository.getTopVendors()
        );


        return dto;
    }

}