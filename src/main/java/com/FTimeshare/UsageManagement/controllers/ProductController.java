package com.FTimeshare.UsageManagement.controllers;


import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.dtos.UserDto;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductEntity> productEntities = productService.getAllProducts();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<List<ProductDto>> getProductsByUserID(@PathVariable int user_id) {
        List<ProductEntity> productEntities = productService.getProductsByUserID(user_id);
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    @PostMapping("/delete/{productID}/{user_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productID") int productID, @PathVariable("user_id") int user_id){
        try {
            productService.deleteProduct(productID, user_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Log the exception and handle it appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        productDto.setProductPicture(productEntity.getProductPicture());
        productDto.setProductID(productEntity.getProjectID().getProjectID());
        productDto.setUserID(productEntity.getUserID().getUserID());
        productDto.setProductTypeID(productEntity.getProductTypeID().getProductTypeID());
        return productDto;
    }
}
