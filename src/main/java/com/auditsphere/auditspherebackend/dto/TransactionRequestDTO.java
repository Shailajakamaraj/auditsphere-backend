package com.auditsphere.auditspherebackend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


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
    private Double amount;





    @NotNull(
            message = "Transaction date is required"
    )
    private LocalDate transactionDate;





    @NotBlank(
            message = "Category is required"
    )
    private String category;





    @NotBlank(
            message = "Status is required"
    )
    private String status;


}