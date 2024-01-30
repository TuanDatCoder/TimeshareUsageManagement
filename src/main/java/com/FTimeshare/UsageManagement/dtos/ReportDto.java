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
    private String rId;
    private Date rCreateDate;
    private String rDetail;
    private Boolean rStatus;
    private String userId;
}
