package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query("SELECT p FROM ProductEntity p WHERE p.accID.accID = :accID")
    List<ProductEntity> findByAccountID(@Param("accID") int accID);

    List<ProductEntity> findByProductStatus(String productStatus);

    @Query("SELECT p FROM ProductEntity p WHERE p.productID = :productID")
    ProductEntity findByProductID(@Param("productID") int productID);

    // List<ProductEntity> findByProductNameContainingIgnoreCase(String name);
    List<ProductEntity> findByProductNameContainingIgnoreCaseAndProductStatus(String productName, String productStatus);

    @Query("SELECT DISTINCT p.productStatus FROM ProductEntity p")
    List<String> findAllProductStatuses();

    List<ProductEntity> findByProductTypeID_ProductTypeID(int productTypeID);
}
