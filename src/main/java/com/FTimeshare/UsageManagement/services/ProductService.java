package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.controllers.ProductController;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    private ProductController productController;
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
    //Goi tat ca san pham
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    //Goi cac danh sach san pham tang dan theo view
    public List<ProductEntity> getAllProductsAscendingByView() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "productViewer"));
    }

    //Goi cac danh sach san pham giam dan theo view
    public List<ProductEntity> getAllProductsDescendingByView() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "productViewer"));
    }

    //Goi cac danh sach san pham tang dan theo area
    public List<ProductEntity> getAllProductsAscendingByArea() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "productArea"));
    }

    //Goi cac danh sach san pham giam dan theo area
    public List<ProductEntity> getAllProductsDescendingByArea() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "productArea"));
    }

    //Goi cac danh sach san pham tang dan theo price
    public List<ProductEntity> getAllProductsAscendingByPrice() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "productPrice"));
    }

    //Goi cac danh sach san pham giam dan theo price
    public List<ProductEntity> getAllProductsDescendingByPrice() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "productPrice"));
    }

    //Tinh tong tien
    public List<ProductEntity> getProductsByUserID(int userID) {

        return productRepository.findByUserID(userID);
    }
    public ProductDto editProduct(int productID, ProductDto productDto) {

        ProductEntity productEntity = productRepository.findByProductID(productID)
                .orElseThrow(() -> new RuntimeException("Prodcut not found with id: " + productID));

        productEntity = productController.convertToEntity(productDto);

        ProductEntity savedProduct = productRepository.save(productEntity);

        return productController.convertToDto(savedProduct);
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