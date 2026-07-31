package com.auditsphere.auditspherebackend.dto;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class RiskSummaryDTO {


    private String riskLevel;


    private Long count;



    public RiskSummaryDTO(){

    }



    public RiskSummaryDTO(
            String riskLevel,
            Long count
    ){

        this.riskLevel = riskLevel;
        this.count = count;

    }


}