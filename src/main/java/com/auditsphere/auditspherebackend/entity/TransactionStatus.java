package com.auditsphere.auditspherebackend.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum TransactionStatus {


    PENDING,
    APPROVED,
    REJECTED,
    COMPLETED;



    @JsonCreator
    public static TransactionStatus fromString(String value){

        if(value == null){
            return null;
        }


        return TransactionStatus.valueOf(
                value.toUpperCase()
        );

    }



    @JsonValue
    public String toValue(){

        return this.name();

    }


}