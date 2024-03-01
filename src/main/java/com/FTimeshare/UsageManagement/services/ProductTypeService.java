package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ProductTypeDto;
import com.FTimeshare.UsageManagement.entities.ProductTypeEntity;
import com.FTimeshare.UsageManagement.repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductTypeDto> getAllProductType() {
        List<ProductTypeEntity> productType = productTypeRepository.findAll();
        return productType.stream()
                .map(productTypeEntity -> new ProductTypeDto(
                        productTypeEntity.getProductTypeID(),
                        productTypeEntity.getProductTypeName()))
                .collect(Collectors.toList());
    }

}
