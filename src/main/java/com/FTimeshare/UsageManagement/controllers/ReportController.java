package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.entities.ReportEntity;
import com.FTimeshare.UsageManagement.repositories.ReportRepository;
import com.FTimeshare.UsageManagement.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    private final ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportService reportService, ReportRepository reportRepository) {
        this.reportService = reportService;
        this.reportRepository = reportRepository;
    }


    @GetMapping("/viewByProductId/{productID}")
    public ResponseEntity<List<ReportDto>> viewReportByProductID(@PathVariable int productID) {
        List<ReportDto> reports = reportService.viewReportByProductID(productID);
        return new ResponseEntity<>(reports, HttpStatus.OK);
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

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllStatus() {
        List<String> statuses = reportService.getAllStatus();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
    @PostMapping("/customer/submitreport")
    public ResponseEntity<ReportDto> submitReport(@RequestBody ReportDto reportDto) {
        LocalDateTime now = LocalDateTime.now();
        reportDto.setReportCreateDate(now);
        ReportDto submittedReport = reportService.submitReport(reportDto);
        return new ResponseEntity<>(submittedReport, HttpStatus.CREATED);
    }

    @PutMapping("update/{reportID}")
    public ResponseEntity<?> updateReport(@PathVariable int reportID, @RequestBody ReportDto updatedReport) {
        LocalDateTime now = LocalDateTime.now();
        updatedReport.setReportCreateDate(now);
        ReportDto editedFeedback = reportService.editFeedback(reportID, updatedReport);
        return ResponseEntity.ok(editedFeedback);

    }


    // API endpoint để xóa một báo cáo dựa trên reportID
    @DeleteMapping("delete/{reportID}")
    public void deleteReport(@PathVariable int reportID) {
        reportRepository.deleteById(reportID);
    }


    LocalDateTime now = LocalDateTime.now();

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


    // Bổ sung productID từ ReportEntity (nếu có)
    if (reportEntity.getProductID() != null) {
        reportDto.setProductID(reportEntity.getProductID().getProductID());
    }

    return reportDto;
}


}
