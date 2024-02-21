package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ReportDto;

import java.util.List;

public interface ReportService {
    List<ReportDto> getAllReport();

    ReportDto submitFeedback(ReportDto reportDto);

    ReportDto deleteReport(int reportID);
}
