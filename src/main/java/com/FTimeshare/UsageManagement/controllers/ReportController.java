package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/customerview")
    public ResponseEntity<List<ReportDto>> getAllReport() {
        List<ReportDto> report = reportService.getAllReport();
        return ResponseEntity.ok(report);
    }

    @PostMapping("/customer/submit-report")
    public ResponseEntity<ReportDto> submitReport(@RequestBody ReportDto reportDto) {
        ReportDto submittedReport = reportService.submitFeedback(reportDto);
        return new ResponseEntity<>(submittedReport, HttpStatus.CREATED);
    }

    @DeleteMapping("/customer/delete-report/{reportID}")
    public ResponseEntity<?> deleteReport(@PathVariable int reportID) {
       ReportDto deletedReport = reportService.deleteReport(reportID);

        if (deletedReport != null) {
            return ResponseEntity.ok(deletedReport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
