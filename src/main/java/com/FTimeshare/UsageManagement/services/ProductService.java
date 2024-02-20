package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }
    public List<ProductEntity> getProductsByUserID(int userID) {

        return productRepository.findByUserID(userID);
    }

    public void deleteProduct(int productID, int user_id) {
        Optional<ProductEntity> product = productRepository.findById(productID);
        if(product.isPresent()&&!product.get().getProductStatus().equalsIgnoreCase("active_booked ")&&product.get().getUserID().getUserID() == user_id){
            productRepository.deleteById(productID);
        }
    }
}
