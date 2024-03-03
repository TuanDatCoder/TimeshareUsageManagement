package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.PictureEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    @Query("SELECT DISTINCT e.reportStatus FROM ReportEntity e")
    List<String> findAllStatus();
    List<ReportEntity> findByProductID_ProductID(int productID);
    
}
