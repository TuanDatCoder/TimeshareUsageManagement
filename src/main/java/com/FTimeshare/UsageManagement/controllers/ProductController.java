package com.FTimeshare.UsageManagement.controllers;


import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import com.FTimeshare.UsageManagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/view_all")//View tat ca san pham
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductEntity> productEntities = productService.getAllProducts();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }
    @GetMapping("/{user_id}")//View san pham thuoc ve 1 nguoi dung
    public ResponseEntity<List<ProductDto>> getProductsByUserID(@PathVariable int user_id) {
        List<ProductEntity> productEntities = productService.getProductsByAccID(user_id);
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    @DeleteMapping("delete/{productID}")//delete san pham
    public void deleteReport(@PathVariable int productID) {
        productRepository.deleteById(productID);
    }



    private List<ProductDto> convertToDtoList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setProductID(productEntity.getProductID());
        productDto.setProductName(productEntity.getProductName());
        productDto.setProductDescription(productEntity.getProductDescription());
        productDto.setProductConvenience(productEntity.getProductConvenience());
        productDto.setProductArea(productEntity.getProductArea());
        productDto.setProductPrice(productEntity.getProductPrice());
        productDto.setAvailableEndDate(productEntity.getAvailableEndDate());
        productDto.setAvailableStartDate(productEntity.getAvailableStartDate());
        productDto.setProductStatus(productEntity.getProductStatus());
        productDto.setProductID(productEntity.getProjectID().getProjectID());
        productDto.setProductViewer(productEntity.getProductViewer());
        productDto.setProductTypeID(productEntity.getProductTypeID().getProductTypeID());
        return productDto;
    }
}
