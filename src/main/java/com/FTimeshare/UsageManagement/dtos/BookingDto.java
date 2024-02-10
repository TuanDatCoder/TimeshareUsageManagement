package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private String bookingID;
    private LocalDateTime startDate;
    private  LocalDateTime endDate;
    private float bookingPrice;
    private String userID;
    private String productID;
}
