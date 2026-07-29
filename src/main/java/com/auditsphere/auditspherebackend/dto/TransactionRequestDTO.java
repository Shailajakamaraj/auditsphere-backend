package com.auditsphere.auditspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequestDTO {

    private String invoiceNumber;

    private String vendorName;

    private Double amount;

    private LocalDate transactionDate;

    private String category;

    private String status;

}