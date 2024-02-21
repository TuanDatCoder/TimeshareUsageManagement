package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class  ProductService {


    @Autowired
    private ProductRepository productRepository;
    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }
}
