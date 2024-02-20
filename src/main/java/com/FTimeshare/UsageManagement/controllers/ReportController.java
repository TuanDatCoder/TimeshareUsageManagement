package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.ReportDto;
import com.FTimeshare.UsageManagement.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
