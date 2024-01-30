package com.FTimeshare.UsageManagement.repositories;
import com.FTimeshare.UsageManagement.entities.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, String> {
}
