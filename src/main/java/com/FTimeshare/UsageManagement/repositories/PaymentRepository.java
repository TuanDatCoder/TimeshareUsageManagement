package com.FTimeshare.UsageManagement.repositories;


import com.FTimeshare.UsageManagement.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
}
