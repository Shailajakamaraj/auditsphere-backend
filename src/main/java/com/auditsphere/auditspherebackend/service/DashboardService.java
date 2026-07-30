package com.auditsphere.auditspherebackend.service;


import com.auditsphere.auditspherebackend.dto.DashboardSummaryDTO;
import com.auditsphere.auditspherebackend.entity.AIReport;
import com.auditsphere.auditspherebackend.entity.Transaction;

import com.auditsphere.auditspherebackend.repository.AIReportRepository;
import com.auditsphere.auditspherebackend.repository.TransactionRepository;

import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class DashboardService {



    private final TransactionRepository transactionRepository;

    private final GeminiService geminiService;

    private final AIReportRepository aiReportRepository;





    public DashboardService(
            TransactionRepository transactionRepository,
            GeminiService geminiService,
            AIReportRepository aiReportRepository
    ){

        this.transactionRepository = transactionRepository;

        this.geminiService = geminiService;

        this.aiReportRepository = aiReportRepository;

    }









    // ==============================
    // DASHBOARD KPI SUMMARY
    // ==============================


    public DashboardSummaryDTO getDashboardSummary(){



        List<Transaction> transactions =
                transactionRepository.findAll();





        long totalTransactions =
                transactions.size();






        double totalAmount =

                transactions.stream()

                        .filter(transaction ->
                                transaction.getAmount()!=null
                        )

                        .mapToDouble(
                                Transaction::getAmount
                        )

                        .sum();







        long highRiskTransactions =

                transactions.stream()

                        .filter(transaction ->

                                "HIGH"
                                        .equals(
                                                transaction.getRiskLevel()
                                        )

                        )

                        .count();







        long duplicateTransactions =

                transactions.stream()

                        .filter(transaction ->

                                transaction.getRiskReason()!=null
                                        &&
                                        transaction.getRiskReason()
                                                .contains("Duplicate")

                        )

                        .count();







        return new DashboardSummaryDTO(

                totalTransactions,

                totalAmount,

                highRiskTransactions,

                duplicateTransactions

        );

    }









    // ==============================
    // GENERATE AI INSIGHT
    // ==============================


    public String generateInsight(){



        AIReport existingReport =

                aiReportRepository
                        .findTopByOrderByCreatedAtDesc();





        if(existingReport != null){

            return existingReport.getReport();

        }








        List<Transaction> transactions =

                transactionRepository.findAll();







        long highRisk =

                transactions.stream()

                        .filter(transaction ->

                                "HIGH"
                                        .equals(
                                                transaction.getRiskLevel()
                                        )

                        )

                        .count();








        long mediumRisk =

                transactions.stream()

                        .filter(transaction ->

                                "MEDIUM"
                                        .equals(
                                                transaction.getRiskLevel()
                                        )

                        )

                        .count();








        long duplicateInvoices =

                transactions.stream()

                        .filter(transaction ->


                                transaction.getRiskReason()!=null
                                        &&
                                        transaction.getRiskReason()
                                                .contains("Duplicate")

                        )

                        .count();








        String prompt = """

                You are an Internal Audit AI assistant.

                Analyze the following financial audit data.

                High Risk Transactions:
                %d


                Medium Risk Transactions:
                %d


                Duplicate Invoice Patterns:
                %d


                Generate a professional audit report containing:

                1. Risk Summary

                2. Possible Fraud Indicators

                3. Recommended Audit Actions


                Keep the response concise and professional.

                """
                .formatted(
                        highRisk,
                        mediumRisk,
                        duplicateInvoices
                );








        String result;



        try{


            result =
                    geminiService
                            .generateInsight(prompt);


        }
        catch(Exception e){


            result =
                    """
                    AI analysis service is currently unavailable.

                    Please manually review:
                    - High risk transactions
                    - Duplicate invoices
                    - Suspicious vendor activity
                    """;


        }








        AIReport report =

                new AIReport(result);




        aiReportRepository.save(report);






        return result;

    }









    // ==============================
    // ADMIN REFRESH AI REPORT
    // ==============================


    public void refreshAIReport(){



        aiReportRepository.deleteAll();



        generateInsight();


    }


}