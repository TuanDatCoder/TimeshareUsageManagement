package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.entities.ReportEntity;
import com.FTimeshare.UsageManagement.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<ReportDto> getAllReport() {
        List<ReportEntity> report = reportRepository.findAll();
        return report.stream()
                .map(reportEntity -> new ReportDto(
                        reportEntity.getReportID(),
                        reportEntity.getReportCreateDate(),
                        reportEntity.getReportDetail(),
                        reportEntity.getReportStatus(),
                        reportEntity.getAccID().getAccID()))
                .collect(Collectors.toList());
    }
}

