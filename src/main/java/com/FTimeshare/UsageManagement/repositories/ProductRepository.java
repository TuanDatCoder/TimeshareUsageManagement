package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query("SELECT p FROM ProductEntity p WHERE p.accID.accID= :accID")
    List<ProductEntity> findByUserID(@Param("accID") int accID);

    @Query("SELECT p FROM ProductEntity p WHERE p.productID = :productID")
    ProductEntity findByProductID(@Param("productID") int productID);
}
