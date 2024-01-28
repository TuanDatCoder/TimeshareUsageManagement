package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private String bookingID;
    private Date starDate;
    private Date endDate;
    private float bookingPrice;
    private String userID;
    private String productID;
}
