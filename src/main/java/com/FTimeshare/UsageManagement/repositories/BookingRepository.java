package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {

}
