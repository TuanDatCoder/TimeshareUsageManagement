package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ProjectTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectTypeEntity, String> {
}
