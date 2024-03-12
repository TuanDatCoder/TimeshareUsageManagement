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
    private int bookingID;
    private LocalDateTime startDate;
    private  LocalDateTime endDate;
    private  LocalDateTime createDate;
    private float bookingPrice;
    private int bookingPerson;
    private String bookingStatus;
    private String imgName;
    private byte[] imgData;
    private String imgRespondName;
    private byte[] imgRespondData;
    private int accID;
    private int productID;


}
