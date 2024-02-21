package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.entities.*;
import com.FTimeshare.UsageManagement.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                        reportEntity.getAccID().getAccID(),
                        reportEntity.getProductID().getProductID()))
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto submitFeedback(ReportDto reportDto) {
        ReportEntity reportEntity = convertToEntity(reportDto);
        ReportEntity savedReport = reportRepository.save(reportEntity);
        return convertToDto(savedReport);
    }

    @Override
    public ReportDto deleteReport(int reportID) {
            // Tìm đặt phòng theo ID
            Optional<ReportEntity> reportEntityOptional = reportRepository.findById(String.valueOf(reportID));

            if (reportEntityOptional.isPresent()) {
                ReportEntity reportEntity = reportEntityOptional.get();

                // Kiểm tra xem người dùng có quyền xóa đặt phòng hay không (tùy thuộc vào logic của bạn)

                // Xóa đặt phòng
                reportRepository.delete(reportEntity);

                // Chuyển đổi và trả về DTO của đặt phòng đã xóa
                return convertToDto(reportEntity);
            } else {
                // Xử lý trường hợp không tìm thấy đặt phòng
                return null;
            }
        }


    private ReportDto convertToDto(ReportEntity reportDto) {
        return new ReportDto(
                reportDto.getReportID() ,
                reportDto.getReportCreateDate(),
                reportDto.getReportDetail(),
                reportDto.getReportStatus(),
                reportDto.getAccID().getAccID(),
                reportDto.getProductID().getProductID()
        );
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


}

