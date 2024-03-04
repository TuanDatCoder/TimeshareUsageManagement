package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private int productID;
    private String productName;
    private float productArea;
    private String productAddress;
    private String productDescription;
    private String productConvenience;
    private float productPrice;
    private LocalDateTime availableStartDate;
    private LocalDateTime availableEndDate;
    private String productStatus;
    private int productPerson;
    private float productRating;
    private int productSale;
    private int productViewer;
    private int projectID;
    private int productTypeID;
    private int accID;

}
