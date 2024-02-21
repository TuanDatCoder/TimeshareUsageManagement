package com.FTimeshare.UsageManagement.repositories;


import com.FTimeshare.UsageManagement.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
}
