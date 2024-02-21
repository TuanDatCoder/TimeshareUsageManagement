package com.FTimeshare.UsageManagement.controllers;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/customerview")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        List<ProductEntity> products = productService.getAllProduct();
        List<ProductDto> productDtos = products.stream()
                .map(productEntity ->  new ProductDto(
                        productEntity.getProductID(),
                        productEntity.getProductName(),
                        productEntity.getProductDescription(),
                        productEntity.getProductConvenience(),
                        productEntity.getProductArea(),
                        productEntity.getProductPrice(),
                        productEntity.getAvailableStartDate(),
                        productEntity.getAvailableEndDate(),
                        productEntity.getProductStatus(),
                        productEntity.getProductViewer(),
                        productEntity.getProjectID().getProjectID(),
                        productEntity.getProductTypeID().getProductTypeID()))
               .collect(Collectors.toList());

        return ResponseEntity.ok().body(productDtos);
    }

}


