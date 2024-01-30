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
public class ReportDto {
    private String reportID;
    private Date reportCreateDate;
    private String reportDetail;
    private String reportStatus;
    private String userID;
}
