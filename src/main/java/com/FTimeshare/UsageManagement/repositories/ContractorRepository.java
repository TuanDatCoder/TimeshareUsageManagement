package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ContractorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepository extends JpaRepository<ContractorEntity, Integer> {
}
