package com.FTimeshare.UsageManagement.controllers;
import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.entities.ProductTypeEntity;
import com.FTimeshare.UsageManagement.entities.ProjectEntity;
import com.FTimeshare.UsageManagement.services.BookingService;
import com.FTimeshare.UsageManagement.services.FeedbackService;
import com.FTimeshare.UsageManagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FeedbackService feedbackService;
    // Đạt

    // Làm Select Option
    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllProductStatuses() {
        List<String> productStatuses = productService.getAllProductStatuses();
        return ResponseEntity.ok(productStatuses);
    }
    // search

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByNameAndStatus(
            @RequestParam("productName") String productName,
            @RequestParam("productStatus") String productStatus) {
        List<ProductEntity> products = productService.findByProductNameContainingIgnoreCaseAndProductStatus(productName, productStatus);
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }



    // Change Status

    @PutMapping("staff/close/{productID}")
    public ResponseEntity<String> closeProduct(@PathVariable int productID) {
        productService.statusProduct(productID,"Closed");
        return ResponseEntity.ok("Done");
    }
    @PutMapping("staff/active/{productID}")
    public ResponseEntity<String> activeProduct(@PathVariable int productID) {
        productService.statusProduct(productID,"Active");
        return ResponseEntity.ok("Done");
    }
    @PutMapping("staff/reject/{productID}")
    public ResponseEntity<String> rejectProduct(@PathVariable int productID) {
        productService.statusProduct(productID,"Rejected");
        return ResponseEntity.ok("Done");
    }
    @PutMapping("staff/pending/{productID}")
    public ResponseEntity<String> pendingProduct(@PathVariable int productID) {
        productService.statusProduct(productID,"Pending");
        return ResponseEntity.ok("Done");
    }

    //view status
    public ResponseEntity<List<ProductDto>> getStatusProducts(String status) {
        List<ProductEntity> statusProducts = productService.getProductsByStatus(status);
        return ResponseEntity.ok(convertToDtoList(statusProducts));
    }

    @GetMapping("staff/active")
    public ResponseEntity<List<ProductDto>> getActiveProducts() {
        return getStatusProducts("Active");
    }
    @GetMapping("staff/pending")
    public ResponseEntity<List<ProductDto>> getPendingProducts() {
        return getStatusProducts("Pending");
    }

    @GetMapping("staff/closed")
    public ResponseEntity<List<ProductDto>> getClosedProducts() {
        return getStatusProducts("Closed");
    }

    @GetMapping("staff/rejected")
    public ResponseEntity<List<ProductDto>> getRejectedProducts() {
        return getStatusProducts("Rejected");
    }

    // Các phương thức hỗ trợ chuyển đổi từ entity sang DTO và từ danh sách entity sang danh sách DTO
    // total

    @GetMapping("staff/totalPending")
    public int countPendingProducts() {
        ResponseEntity<List<ProductDto>> responseEntity = getStatusProducts("Pending");
        List<ProductDto> pendingProducts = responseEntity.getBody();
        return pendingProducts.size();
    }

    @GetMapping("staff/totalActive")
    public int countActiveProducts() {
        ResponseEntity<List<ProductDto>> responseEntity = getStatusProducts("Active");
        List<ProductDto> pendingProducts = responseEntity.getBody();
        return pendingProducts.size();
    }

    @GetMapping("staff/totalClosed")
    public int countClosedProducts() {
        ResponseEntity<List<ProductDto>> responseEntity = getStatusProducts("Close");
        List<ProductDto> closeProducts = responseEntity.getBody();
        return closeProducts.size();
    }
    @GetMapping("staff/totalRejected")
    public int countRejectedProducts() {
        ResponseEntity<List<ProductDto>> responseEntity = getStatusProducts("Rejected");
        List<ProductDto> rejectedProducts = responseEntity.getBody();
        return rejectedProducts.size();
    }

    // Quý
    @GetMapping("/view_all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductEntity> productEntities = productService.getAllProducts();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    @GetMapping("/viewById/{productID}")
    public ResponseEntity<List<ProductDto>> viewProductById(@PathVariable int productID) {
        List<ProductDto> product = productService.getProductByBookingId(productID);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/viewByProductTypeId/{productTypeID}")
    public ResponseEntity<List<ProductDto>> viewProductByProductTypeId(@PathVariable int productTypeID) {
        List<ProductDto> products = productService.getProductByProductTypeId(productTypeID);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    //Goi cac danh sach san pham tang dan theo view
    @GetMapping("/view/by_viewer/ascending")
    public ResponseEntity<List<ProductDto>> getProductsByViewerAscending() {
        List<ProductEntity> productEntities = productService.getAllProductsAscendingByView();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }


    //Goi cac danh sach san pham giam dan theo view
    @GetMapping("/view/by_viewer/descending")
    public ResponseEntity<List<ProductDto>> getProductsByViewerDescending() {
        List<ProductEntity> productEntities = productService.getAllProductsDescendingByView();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    @GetMapping("/view/by_area/ascending")
    public ResponseEntity<List<ProductDto>> getProductsByAreaAscending() {
        List<ProductEntity> productEntities = productService.getAllProductsAscendingByArea();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    //Goi cac danh sach san pham giam dan theo view
    @GetMapping("/view/by_area/descending")
    public ResponseEntity<List<ProductDto>> getProductsByAreaDescending() {
        List<ProductEntity> productEntities = productService.getAllProductsDescendingByArea();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    @GetMapping("/view/by_price/ascending")
    public ResponseEntity<List<ProductDto>> getProductsByPriceAscending() {
        List<ProductEntity> productEntities = productService.getAllProductsAscendingByPrice();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    //Goi cac danh sach san pham giam dan theo view
    @GetMapping("/view/by_price/descending")
    public ResponseEntity<List<ProductDto>> getProductsByPriceDescending() {
        List<ProductEntity> productEntities = productService.getAllProductsDescendingByPrice();
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<ProductDto>> getProductsByUserID(@PathVariable int user_id) {
        List<ProductEntity> productEntities = productService.getProductsByAccountID(user_id);
        return ResponseEntity.ok(convertToDtoList(productEntities));
    }
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto productDto) {
        ProductEntity productEntity = convertToEntity(productDto);
        productEntity = productService.addNewProduct(productEntity);
        ProductDto responseDto = convertToDto(productEntity);
        return ResponseEntity.ok(responseDto);
    }

    //Delete product, truyền vào userID của customer, nếu đúng là chủ của product thì đc sửa
    @PutMapping("/edit/{product_id}/{user_id}")
    public ResponseEntity<?> editProducts(@PathVariable int product_id, @PathVariable int user_id, @RequestBody  ProductDto updateProduct) {
        ProductDto editProduct = productService.editProduct(product_id, user_id, updateProduct);
        return ResponseEntity.ok(editProduct);
    }

    //Delete product, truyền vào userID của customer, nếu đúng là chủ của product và product đang ko có booking thì đc xóa
    //Tra ve product sau khi sua
    @PostMapping("/delete/{productID}/{user_id}")
    public List<ProductEntity> deleteProduct(@PathVariable("productID") int productID, @PathVariable("user_id") int user_id){

        productService.deleteProduct(productID, user_id);
        return productService.getAllProducts();

    }
    @DeleteMapping("/delete/{productID}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productID) {
        ProductDto deleteProduct= productService.deleteProductID(productID);

        if (deleteProduct != null) {
            return ResponseEntity.ok(deleteProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sum/{user_id}")
    public float getSumOfAllProductByUserID( @PathVariable("user_id") int user_id){
        List<ProductEntity> productEntities = productService.getProductsByAccountID(user_id);
        float sum = 0;
        for(int i = 0; i<productEntities.size(); i++){
            sum+= (float) bookingService.getSumPriceByProductId(productEntities.get(i).getProductID());
        }
        return sum;
    }
    private List<ProductDto> convertToDtoList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto convertToDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setProductID(productEntity.getProductID());
        productDto.setProductName(productEntity.getProductName());
        productDto.setProductArea(productEntity.getProductArea());
        productDto.setProductAddress(productEntity.getProductAddress());
        productDto.setProductDescription(productEntity.getProductDescription());
        productDto.setProductConvenience(productEntity.getProductConvenience());
        productDto.setProductPrice(productEntity.getProductPrice());
        productDto.setAvailableEndDate(productEntity.getAvailableEndDate());
        productDto.setAvailableStartDate(productEntity.getAvailableStartDate());
        productDto.setProductStatus(productEntity.getProductStatus());
        productDto.setProductPerson(productEntity.getProductPerson());
        productDto.setProductRating(productEntity.getProductRating());
        productDto.setProductSale(productEntity.getProductSale());
        productDto.setProductViewer(productEntity.getProductViewer());
        productDto.setProjectID(productEntity.getProjectID().getProjectID());
        productDto.setAccID(productEntity.getAccID().getAccID());
        productDto.setProductTypeID(productEntity.getProductTypeID().getProductTypeID());

        Float averageRating = feedbackService.getAverageFeedbackRatingByProductId(productEntity.getProductID());
        productDto.setProductRating(averageRating != null ? averageRating : 0.0f);
        return productDto;
    }

    public ProductEntity convertToEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductID(productDto.getProductID());
        productEntity.setProductName(productDto.getProductName());
        productEntity.setProductArea(productDto.getProductArea());
        productEntity.setProductAddress(productDto.getProductAddress());
        productEntity.setProductDescription(productDto.getProductDescription());
        productEntity.setProductConvenience(productDto.getProductConvenience());
        productEntity.setProductPrice(productDto.getProductPrice());
        productEntity.setAvailableEndDate(productDto.getAvailableEndDate());
        productEntity.setAvailableStartDate(productDto.getAvailableStartDate());
        productEntity.setProductPerson(productDto.getProductPerson());
        productEntity.setProductRating(productDto.getProductRating());
        productEntity.setProductSale(productDto.getProductSale());
        productEntity.setProductStatus(productDto.getProductStatus());
        productEntity.setProductViewer(productDto.getProductViewer());
        ProjectEntity pj = new ProjectEntity();
        pj.setProjectID(productDto.getProjectID());
        productEntity.setProjectID(pj);
        AccountEntity ac = new AccountEntity();
        ac.setAccID(productDto.getAccID());
        productEntity.setAccID(ac);
        ProductTypeEntity pdt = new ProductTypeEntity();
        pdt.setProductTypeID(productDto.getProductTypeID());
        productEntity.setProductTypeID(pdt);
        return productEntity;
    }

}