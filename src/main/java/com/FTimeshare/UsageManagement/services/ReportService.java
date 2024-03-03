package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.PictureDto;
import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.entities.*;
import com.FTimeshare.UsageManagement.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    public List<ReportEntity> getAllReport() {
        return reportRepository.findAll();
    }

    public List<String> getAllStatus() {
        return reportRepository.findAllStatus();
    }

    public List<ReportDto> viewReportByProductID(int productID) {
        List<ReportEntity> reportEntities = reportRepository.findByProductID_ProductID(productID);
        return reportEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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


    public ReportDto editFeedback(int reportID, ReportDto updatedReport) {
        // Tìm phản hồi cần chỉnh sửa trong cơ sở dữ liệu
        ReportEntity existingReport = reportRepository.findById(reportID)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportID));

        // Cập nhật thông tin của phản hồi
        existingReport.setReportCreateDate(updatedReport.getReportCreateDate());
        existingReport.setReportDetail(updatedReport.getReportDetail());
        existingReport.setReportStatus(updatedReport.getReportStatus());

        // Lưu cập nhật vào cơ sở dữ liệu
        ReportEntity savedReport = reportRepository.save(existingReport);

        // Chuyển đổi và trả về phiên bản cập nhật của phản hồi
        return convertToDto(savedReport);
    }
    }


