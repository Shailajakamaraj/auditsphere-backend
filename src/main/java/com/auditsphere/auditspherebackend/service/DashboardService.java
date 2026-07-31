package com.auditsphere.auditspherebackend.service;


import com.auditsphere.auditspherebackend.dto.DashboardSummaryDTO;
import com.auditsphere.auditspherebackend.dto.MonthlySummaryDTO;
import com.auditsphere.auditspherebackend.dto.RiskSummaryDTO;


import com.auditsphere.auditspherebackend.entity.AIReport;
import com.auditsphere.auditspherebackend.entity.RiskLevel;
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
    // DASHBOARD SUMMARY
    // ==============================


    public DashboardSummaryDTO getDashboardSummary(){



        List<Transaction> transactions =
                transactionRepository.findAll();



        long totalTransactions =
                transactions.size();



        double totalAmount =
                transactions.stream()

                        .mapToDouble(t ->
                                t.getAmount()==null
                                        ?
                                        0
                                        :
                                        t.getAmount().doubleValue()
                        )

                        .sum();





        long highRiskTransactions =
                transactionRepository.countByRiskLevel(
                        RiskLevel.HIGH
                );





        long duplicateTransactions =
                transactionRepository
                        .findDuplicateTransactions()
                        .size();





        return new DashboardSummaryDTO(

                totalTransactions,

                totalAmount,

                highRiskTransactions,

                duplicateTransactions

        );


    }









    // ==============================
    // AI INSIGHTS
    // ==============================


    public String generateInsight(){



        AIReport existing =
                aiReportRepository
                        .findTopByOrderByCreatedAtDesc();



        if(existing != null){

            return existing.getReport();

        }





        List<Transaction> transactions =
                transactionRepository.findAll();





        long high =
                transactions.stream()

                        .filter(t ->
                                RiskLevel.HIGH
                                        .equals(t.getRiskLevel())
                        )

                        .count();





        long medium =
                transactions.stream()

                        .filter(t ->
                                RiskLevel.MEDIUM
                                        .equals(t.getRiskLevel())
                        )

                        .count();





        long duplicate =
                transactionRepository
                        .findDuplicateTransactions()
                        .size();







        String prompt = """

        You are an Internal Audit AI.

        Analyze the financial data.

        Total Transactions : %d

        High Risk : %d

        Medium Risk : %d

        Duplicate Transactions : %d


        Generate:

        1. Executive Summary

        2. Fraud Indicators

        3. Recommendations


        Use professional audit language.

        """.formatted(

                transactions.size(),

                high,

                medium,

                duplicate

        );





        String aiResult =
                geminiService.generateInsight(prompt);





        AIReport report =
                new AIReport(aiResult);





        aiReportRepository.save(report);





        return aiResult;


    }









    // ==============================
    // REFRESH AI REPORT
    // ==============================


    public void refreshAIReport(){

        aiReportRepository.deleteAll();

    }









    // ==============================
    // MONTHLY SUMMARY
    // ==============================


    public List<MonthlySummaryDTO> getMonthlySummary(){



        return transactionRepository
                .getMonthlySummary()

                .stream()

                .map(row ->

                        new MonthlySummaryDTO(

                                getMonthName(
                                        ((Number)row[1])
                                                .intValue()
                                ),

                                ((Number)row[2])
                                        .longValue()

                        )

                )

                .toList();


    }









    // ==============================
    // RISK SUMMARY
    // ==============================


    public List<RiskSummaryDTO> getRiskSummary(){



        return transactionRepository
                .getRiskSummary()

                .stream()

                .map(row ->


                        new RiskSummaryDTO(

                                row[0] != null
                                        ?
                                        row[0].toString()
                                        :
                                        "UNKNOWN",


                                ((Number)row[1])
                                        .longValue()

                        )

                )

                .toList();


    }









    private String getMonthName(
            int month
    ){


        return switch(month){

            case 1 -> "Jan";
            case 2 -> "Feb";
            case 3 -> "Mar";
            case 4 -> "Apr";
            case 5 -> "May";
            case 6 -> "Jun";
            case 7 -> "Jul";
            case 8 -> "Aug";
            case 9 -> "Sep";
            case 10 -> "Oct";
            case 11 -> "Nov";
            case 12 -> "Dec";

            default -> "Unknown";

        };


    }



}