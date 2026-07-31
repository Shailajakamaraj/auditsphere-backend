package com.auditsphere.auditspherebackend.dto;


import com.auditsphere.auditspherebackend.entity.TransactionStatus;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;



@Getter
@Setter
public class TransactionRequestDTO {



    @NotBlank(
            message = "Invoice number is required"
    )
    private String invoiceNumber;




    @NotBlank(
            message = "Vendor name is required"
    )
    private String vendorName;





    @NotNull(
            message = "Amount is required"
    )
    @Positive(
            message = "Amount must be greater than zero"
    )
    private BigDecimal amount;





    @NotNull(
            message = "Transaction date is required"
    )
    private LocalDate transactionDate;





    @NotBlank(
            message = "Category is required"
    )
    private String category;





    @NotNull(
            message = "Status is required"
    )
    private TransactionStatus status;



}