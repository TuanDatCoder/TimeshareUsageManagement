package com.FTimeshare.UsageManagement.repositories;


import com.FTimeshare.UsageManagement.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    Optional<PaymentEntity> findByImgName(String fileName);

    List<PaymentEntity> findByAccID_AccID(int accId);
}
