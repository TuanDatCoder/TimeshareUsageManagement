package com.FTimeshare.UsageManagement.services;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void deleteProduct(int productID, int acc_id) {
        Optional<ProductEntity> product = productRepository.findById(productID);
        if(product.isPresent()&&!product.get().getProductStatus().equalsIgnoreCase("active_booked ")&&product.get().getAccID().getAccID() == acc_id){
            productRepository.deleteById(productID);
        }
    }

//    public List<ProductEntity> searchProductsByName(String name) {
//        return productRepository.findByProductNameContainingIgnoreCase(name);
//    }
public List<ProductEntity> findByProductNameContainingIgnoreCaseAndProductStatus(String productName, String productStatus) {
    return productRepository.findByProductNameContainingIgnoreCaseAndProductStatus(productName, productStatus);
}

    public List<String> getAllProductStatuses() {
        return productRepository.findAllProductStatuses();
    }


//    public List<BookingDto> getBookingsByBookingId(int bookingID) {
//        Optional<BookingEntity> bookingEntities = bookingRepository.findById(bookingID);
//        return bookingEntities.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

    public List<ProductDto> getProductByBookingId(int productID) {
        Optional<ProductEntity> productEntities = productRepository.findById(productID);
        return productEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(ProductEntity productEntity) {
        // Your existing DTO conversion logic
        return new ProductDto(
                productEntity.getProductID(),
                productEntity.getProductName(),
                productEntity.getProductDescription(),
                productEntity.getProductConvenience(),
                productEntity.getProductArea(),
                productEntity.getProductPrice(),
                productEntity.getAvailableStartDate(),
                productEntity.getAvailableEndDate(),
                productEntity.getProductStatus(),
                productEntity.getProductPerson(),
                productEntity.getProductRating(),
                productEntity.getProductViewer(),
                productEntity.getProductStatus(),
                productEntity.getProjectID().getProjectID(),
                productEntity.getProductTypeID().getProductTypeID(),
                productEntity.getAccID().getAccID());

    }
}