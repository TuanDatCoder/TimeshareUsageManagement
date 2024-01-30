package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository <FeedbackEntity, String> {
}
