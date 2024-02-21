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
public class ReportDto {
    private int reportID;
    private LocalDateTime reportCreateDate;
    private String reportDetail;
    private String reportStatus;
    private int accID;
    private int productID;
}
