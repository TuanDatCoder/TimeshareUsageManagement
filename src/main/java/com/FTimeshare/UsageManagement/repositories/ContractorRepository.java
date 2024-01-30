package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ContractorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepository extends JpaRepository<ContractorEntity, String> {
}
