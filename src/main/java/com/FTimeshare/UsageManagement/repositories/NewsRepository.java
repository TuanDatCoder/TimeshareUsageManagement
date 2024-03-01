package com.FTimeshare.UsageManagement.repositories;


import com.FTimeshare.UsageManagement.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {

    Optional<NewsEntity> findByImgName(String fileName);
}
