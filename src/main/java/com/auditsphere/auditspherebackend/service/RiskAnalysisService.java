package com.auditsphere.auditspherebackend.service;
import java.math.BigDecimal;

import com.auditsphere.auditspherebackend.entity.RiskLevel;
import com.auditsphere.auditspherebackend.entity.Transaction;
import com.auditsphere.auditspherebackend.repository.TransactionRepository;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class RiskAnalysisService {


    private final TransactionRepository transactionRepository;



    public RiskAnalysisService(
            TransactionRepository transactionRepository
    ){

        this.transactionRepository =
                transactionRepository;

    }






    public void analyzeRisk(
            Transaction transaction
    ){


        int score = 0;


        StringBuilder reason =
                new StringBuilder();


        StringBuilder factors =
                new StringBuilder();





        // High amount check

        if(transaction.getAmount()!=null
                &&
                transaction.getAmount()
                        .compareTo(
                                new BigDecimal("50000")
                        ) > 0){


            score += 20;


            reason.append(
                    "High transaction amount. "
            );


            factors.append(
                    "High amount (+20)\n"
            );

        }






        // Duplicate invoice check

        List<Transaction> existing =
                transactionRepository
                        .findByInvoiceNumber(
                                transaction.getInvoiceNumber()
                        );



        if(!existing.isEmpty()){


            score +=40;


            reason.append(
                    "Duplicate invoice detected. "
            );


            factors.append(
                    "Duplicate invoice (+40)\n"
            );

        }







        // New vendor check


        long vendorCount =
                transactionRepository
                        .countByVendorName(
                                transaction.getVendorName()
                        );



        if(vendorCount==0){


            score +=15;


            reason.append(
                    "New vendor detected. "
            );


            factors.append(
                    "New vendor (+15)\n"
            );

        }








        RiskLevel riskLevel;



        if(score>=60){

            riskLevel =
                    RiskLevel.HIGH;

        }
        else if(score>=30){

            riskLevel =
                    RiskLevel.MEDIUM;

        }
        else{

            riskLevel =
                    RiskLevel.LOW;

        }








        String recommendation;



        if(riskLevel==RiskLevel.HIGH){


            recommendation =
                    "Immediate audit review required. Verify invoice and vendor.";

        }
        else if(riskLevel==RiskLevel.MEDIUM){


            recommendation =
                    "Additional verification recommended.";

        }
        else{


            recommendation =
                    "Transaction appears normal.";

        }








        transaction.setRiskScore(score);


        transaction.setRiskLevel(
                riskLevel
        );


        transaction.setRiskReason(
                reason.toString()
        );


        transaction.setRiskFactors(
                factors.toString()
        );


        transaction.setAiRecommendation(
                recommendation
        );



    }


}