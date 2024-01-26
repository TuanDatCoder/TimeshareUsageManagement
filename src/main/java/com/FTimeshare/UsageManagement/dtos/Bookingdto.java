package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bookingdto {
    private String bookingID;
    private String starDate;
    private String endDate;
    private float bookingPrice;
    private String userID;
    private String productID;
}
