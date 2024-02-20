package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.entities.ReportEntity;
import com.FTimeshare.UsageManagement.repositories.NewsRepository;
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
}
