package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
    private int paymentID;
    private String accountName;
    private String banking;
    private String accountNumber;
    private String imgName;
    private byte[] imgData;
    private int accID;


}
