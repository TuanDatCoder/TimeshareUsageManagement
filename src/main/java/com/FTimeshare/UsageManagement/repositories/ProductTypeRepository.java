package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Integer> {
}
