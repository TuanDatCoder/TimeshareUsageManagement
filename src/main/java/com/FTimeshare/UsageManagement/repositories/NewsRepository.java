package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository <NewsEntity, String> {
}
