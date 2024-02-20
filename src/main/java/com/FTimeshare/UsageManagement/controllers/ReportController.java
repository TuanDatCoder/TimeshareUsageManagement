package com.FTimeshare.UsageManagement.controllers;


import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.entities.ReportEntity;
import com.FTimeshare.UsageManagement.repositories.ReportRepository;
import com.FTimeshare.UsageManagement.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private ReportService reportService;

    private final ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportService reportService, ReportRepository reportRepository) {
        this.reportService = reportService;
        this.reportRepository = reportRepository;
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<ReportDto>> getAllReport() {
        List<ReportEntity> reportEntities = reportService.getAllReport();
        List<ReportDto> reportDto = reportEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportDto);
    }


    // API endpoint để lấy một báo cáo dựa trên reportID
    @GetMapping("viewDetail/{reportID}")
    public ReportEntity getReportById(@PathVariable int reportID) {
        return reportRepository.findById(reportID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo với ID = " + reportID));
    }

    // API endpoint để cập nhật một báo cáo đã tồn tại
    @PutMapping("update/{reportID}")
    public ReportEntity updateReport(@PathVariable int reportID, @RequestBody ReportEntity updatedReport) {
        return reportRepository.findById(reportID)
                .map(report -> {
                    report.setReportDetail(updatedReport.getReportDetail());
                    report.setReportStatus(updatedReport.getReportStatus());
                    // Cập nhật các trường khác nếu cần
                    return reportRepository.save(report);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo với ID = " + reportID));
    }

    // API endpoint để xóa một báo cáo dựa trên reportID
    @DeleteMapping("delete/{reportID}")
    public void deleteReport(@PathVariable int reportID) {
        reportRepository.deleteById(reportID);
    }



private ReportDto convertToDto(ReportEntity reportEntity) {
    ReportDto reportDto = new ReportDto();
    reportDto.setReportID(reportEntity.getReportID());
    reportDto.setReportCreateDate(reportEntity.getReportCreateDate());
    reportDto.setReportDetail(reportEntity.getReportDetail());
    reportDto.setReportStatus(reportEntity.getReportStatus());

    // Bổ sung accID từ ReportEntity
    if (reportEntity.getAccID() != null) {
        reportDto.setAccID(reportEntity.getAccID().getAccID());
    }
    //reportDto.setAccID(reportEntity.getAccID());

    // Bổ sung productID từ ReportEntity (nếu có)
    if (reportEntity.getProductID() != null) {
        reportDto.setProductID(reportEntity.getProductID().getProductID());
    }

    return reportDto;
}


}
