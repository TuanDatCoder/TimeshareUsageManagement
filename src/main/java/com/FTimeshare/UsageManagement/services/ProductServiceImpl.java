package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDto(
                        product.getProductID(),
                        product.getProductName(),
                        product.getProductDescription(),
                        product.getProductConvenience(),
                        product.getProductArea(),
                        product.getProductPrice(),
                        product.getAvailableStartDate(),
                        product.getAvailableEndDate(),
                        product.getProductStatus(),
                        product.getProductViewer(),
                        product.getProjectID().getProjectID(),
                        product.getProductTypeID().getProductTypeID()))
                .collect(Collectors.toList());
    }

}