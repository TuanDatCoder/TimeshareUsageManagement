package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.controllers.ProductController;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.FeedbackRepository;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductController productController;

    @Autowired
    private BookingService bookingService;
    //Đạt
    public List<ProductEntity> getProductsByStatus(String status) {
        return productRepository.findByProductStatus(status);
    }

    public void statusProduct(int productID, String Status) {
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

    public List<ProductEntity> getProductsByAccountID(int accID) {

        return productRepository.findByAccountID(accID);
    }

    public void deleteProduct(int productID, int acc_id) {
        Optional<ProductEntity> product = productRepository.findById(productID);
        if (product.isPresent() && !product.get().getProductStatus().equalsIgnoreCase("active_booked ") && product.get().getAccID().getAccID() == acc_id) {
            productRepository.deleteById(productID);
        }
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
    public int getProductPersonByProductId(int productID){
        return productRepository.findByProductID(productID).getProductPerson();
    }

    public String checkDateForFilter(String startDate, String endDate){
        if(startDate.equals("null")&&endDate.equals("null")){
            return "OK";
        } else if (startDate.equals("null")&&(!endDate.equals("null"))) {
            return "Please input your check-in date";
        } else if ((!startDate.equals("null"))&&endDate.equals("null")) {
            return "Please input your check-out date";
        } else {
            LocalDateTime checkinDate = LocalDateTime.parse(startDate);
            LocalDateTime checkoutDate = LocalDateTime.parse(endDate);
            LocalDateTime present = LocalDateTime.now();
            if(checkinDate.isBefore(present)|| checkoutDate.isBefore(present)){
                return "Check-in date or check-out date is passed";
            }

            if(checkinDate.isAfter(checkoutDate))
                return "Check-out date must after check-in date";
        }
        return "OK";
    }

    //kiểm tra xem thanh phố trong địa chỉ của product có giống thành phố muốn filter hay ko, nếu là all thì auto true
    public Boolean checkContainAddress(String productAddress, String filterCity){
        if(filterCity.equals("All")) return true;
        if(productAddress.contains(filterCity)) return true;

        return false;
    }

    public Boolean checkEmptyDateOfProduct(String startDate, String endDate, ProductEntity product){
        List<BookingEntity> bookings = bookingService.getBookingsByStatusAndProductId("Active", product.getProductID());
        bookings.addAll(bookingService.getBookingsByStatusAndProductId("In progress", product.getProductID()));
        LocalDateTime reqStartDate = LocalDateTime.parse(startDate);
        LocalDateTime reqEndDate = LocalDateTime.parse(endDate);

        for (BookingEntity b: bookings) {
            if(reqStartDate.isAfter(b.getStartDate())&&reqStartDate.isBefore(b.getEndDate()))
                return false;
            if (reqEndDate.isAfter(b.getStartDate())&&reqEndDate.isBefore(b.getEndDate()))
                return false;
            if(reqStartDate.isBefore(b.getStartDate())&&reqEndDate.isAfter(b.getEndDate()))
                return false;
        }
        return true;
    }
    public List<ProductEntity> findByProductNameContainingIgnoreCaseAndProductStatus(String productName, String productStatus) {
        return productRepository.findByProductNameContainingIgnoreCaseAndProductStatus(productName, productStatus);
    }

    public List<String> getAllProductStatuses() {
        return productRepository.findAllProductStatuses();
    }
    public List<ProductDto> getProductByBookingId(int productID) {
        Optional<ProductEntity> productEntities = productRepository.findById(productID);
        return productEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto editProduct(int productID, int userID, ProductDto productDto) {

        ProductEntity productEntity = productRepository.findByProductID(productID);
        if(productEntity.getAccID().getAccID()==userID){

            productEntity = productController.convertToEntity(productDto);
            deleteProduct(productID, userID);
            ProductEntity savedProduct = productRepository.save(productEntity);
            return productController.convertToDto(savedProduct);
        }
        return null;

    }
    private ProductDto convertToDto(ProductEntity productEntity) {
        // Your existing DTO conversion logic
        return new ProductDto(
                productEntity.getProductID(),
                productEntity.getProductName(),
                productEntity.getProductArea(),
                productEntity.getProductAddress(),
                productEntity.getProductDescription(),
                productEntity.getProductConvenience(),
                productEntity.getProductPrice(),
                productEntity.getAvailableStartDate(),
                productEntity.getAvailableEndDate(),
                productEntity.getProductStatus(),
                productEntity.getProductPerson(),
                productEntity.getProductRating(),
                productEntity.getProductSale(),
                productEntity.getProductViewer(),
                productEntity.getProjectID().getProjectID(),
                productEntity.getProductTypeID().getProductTypeID(),
                productEntity.getAccID().getAccID());

    }
    public ProductEntity addNewProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
    public ProductEntity getProductById(int productID) {
        Optional<ProductEntity> productOptional = productRepository.findById(productID);
        return productOptional.orElse(null);
    }

    public List<ProductDto> getProductByProductTypeId(int productTypeID) {
       List<ProductEntity> productEntities = productRepository.findByProductTypeID_ProductTypeID(productTypeID);
        return productEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public ProductDto deleteProductID(int productID) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productID);

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();

            productRepository.delete(productEntity);
            return convertToDto(productEntity);
        } else {
            return null;
        }
    }
}