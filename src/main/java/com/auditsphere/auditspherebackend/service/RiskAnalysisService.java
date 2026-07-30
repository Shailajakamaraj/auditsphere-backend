package com.auditsphere.auditspherebackend.service;


import com.auditsphere.auditspherebackend.entity.Transaction;
import com.auditsphere.auditspherebackend.repository.TransactionRepository;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class RiskAnalysisService {


    private final TransactionRepository transactionRepository;



    public RiskAnalysisService(
            TransactionRepository transactionRepository
    ) {

        this.transactionRepository = transactionRepository;

    }





    public void analyzeRisk(Transaction transaction) {


        System.out.println("🔥 AI RISK ENGINE STARTED");



        int score = 0;


        StringBuilder reason = new StringBuilder();


        StringBuilder factors = new StringBuilder();




        // Rule 1: High Amount Detection

        if (transaction.getAmount() != null &&
                transaction.getAmount() > 50000) {


            score += 20;


            reason.append(
                    "High transaction amount. "
            );


            factors.append(
                    "• High transaction amount (+20)\n"
            );

        }





        // Rule 2: Duplicate Invoice Detection


        List<Transaction> existingTransactions =
                transactionRepository.findByInvoiceNumber(
                        transaction.getInvoiceNumber()
                );



        if (!existingTransactions.isEmpty()) {


            score += 40;


            reason.append(
                    "Duplicate invoice detected. "
            );


            factors.append(
                    "• Duplicate invoice pattern detected (+40)\n"
            );

        }





        // Rule 3: New Vendor Detection


        long vendorCount =
                transactionRepository.countByVendorName(
                        transaction.getVendorName()
                );



        if (vendorCount == 0) {


            score += 15;


            reason.append(
                    "New vendor detected. "
            );


            factors.append(
                    "• New vendor risk detected (+15)\n"
            );

        }







        // Risk Classification


        String riskLevel;



        if(score >= 60) {


            riskLevel = "HIGH";


        }
        else if(score >= 30) {


            riskLevel = "MEDIUM";


        }
        else {


            riskLevel = "LOW";

        }







        // AI Recommendation Generation


        String recommendation;



        if(score >= 60) {


            recommendation =
                    "Immediate audit review required. Verify invoice authenticity and vendor details before approval.";


        }
        else if(score >=30) {


            recommendation =
                    "Perform additional verification before processing this transaction.";


        }
        else {


            recommendation =
                    "Transaction appears normal. Continue regular monitoring.";

        }








        // Store Analysis Result


        transaction.setRiskScore(score);


        transaction.setRiskLevel(riskLevel);


        transaction.setRiskReason(
                reason.toString()
        );


        transaction.setRiskFactors(
                factors.toString()
        );


        transaction.setAiRecommendation(
                recommendation
        );





        System.out.println(
                "FINAL SCORE = " + score
        );


        System.out.println(
                "RISK LEVEL = " + riskLevel
        );


        System.out.println(
                "FACTORS = " + factors
        );

    }

}