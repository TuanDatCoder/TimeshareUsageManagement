package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, String> {
}
