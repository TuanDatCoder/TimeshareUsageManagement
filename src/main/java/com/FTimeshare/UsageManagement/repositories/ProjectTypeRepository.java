package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ProjectTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypeRepository extends JpaRepository<ProjectTypeEntity, Integer> {
}
