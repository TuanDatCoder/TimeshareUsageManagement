package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
import com.FTimeshare.UsageManagement.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository <FeedbackEntity, Integer> {
    List<FeedbackEntity> findByProductID_ProductID(int productID);
}
