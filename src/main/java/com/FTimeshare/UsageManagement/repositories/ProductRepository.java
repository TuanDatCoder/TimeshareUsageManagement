package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
}
