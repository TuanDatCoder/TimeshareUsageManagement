package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
