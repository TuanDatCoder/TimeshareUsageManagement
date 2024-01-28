package com.FTimeshare.UsageManagement.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductDto {
    private String productID;
    private String productName;
    private String productDescription;
    private String productConvinience;
    private float productArea;
    private float productPrice;
    private boolean productStatus;
    private String productPicture;
    private String projectID;
    private String productTypeID;
}
