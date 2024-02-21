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

    //Đạt
    public List<ProductEntity> getProductsByStatus(String status) {
        return productRepository.findByProductStatus(status);
    }

//    public int getTotalProductCount() {
//        return (int) productRepository.count();
//    }

    public void closeProduct(int productID, String Status) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(productID);
        if (optionalProduct.isPresent()) {
            ProductEntity product = optionalProduct.get();
            product.setProductStatus(Status);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại với ID: " + productID);
        }
    }


    // Quý
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }
    public List<ProductEntity> getProductsByUserID(int userID) {

        return productRepository.findByUserID(userID);
    }

    public ProductEntity addNewProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
    public void deleteProduct(int productID, int acc_id) {
        Optional<ProductEntity> product = productRepository.findById(productID);
        if(product.isPresent()&&!product.get().getProductStatus().equalsIgnoreCase("active_booked ")&&product.get().getAccID().getAccID() == acc_id){
            productRepository.deleteById(productID);
        }
    }
}