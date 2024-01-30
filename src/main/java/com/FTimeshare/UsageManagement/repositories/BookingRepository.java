package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingRepository, String> {

}
