package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.entities.*;
import com.FTimeshare.UsageManagement.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    public List<ReportEntity> getAllReport() {
        return reportRepository.findAll();
    }

    public ReportDto submitReport(ReportDto reportDto) {
        ReportEntity reportEntity = convertToEntity(reportDto);
        ReportEntity savedReport = reportRepository.save(reportEntity);
        return convertToDto(savedReport);
    }

    private ReportEntity convertToEntity(ReportDto reportDto) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportID(reportDto.getReportID());
        reportEntity.setReportCreateDate(reportDto.getReportCreateDate());
        reportEntity.setReportDetail(reportDto.getReportDetail());
        reportEntity.setReportStatus(reportDto.getReportStatus());

        // Assume that bookingID is an int in FeedbackDto
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccID(reportDto.getAccID());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductID(reportDto.getProductID());

        // Set the bookingEntity to feedbackEntity
        reportEntity.setAccID(accountEntity);
        reportEntity.setProductID(productEntity);

        return reportEntity;
    }

    private ReportDto convertToDto(ReportEntity reportEntity) {
        return new ReportDto(
                reportEntity.getReportID(),
                reportEntity.getReportCreateDate(),
                reportEntity.getReportDetail(),
                reportEntity.getReportStatus(),
                reportEntity.getAccID().getAccID(),
                reportEntity.getProductID().getProductID()
        );
    }

}
