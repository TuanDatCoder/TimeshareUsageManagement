package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ContractorEntity;
import com.FTimeshare.UsageManagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ContractorEntity, String> {
}
