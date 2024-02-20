package com.FTimeshare.UsageManagement.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String productDescription;
    private String productConvenience;
    private float productArea;
    private float productPrice;
    private LocalDateTime availableStartDate;
    private  LocalDateTime availableEndDate;
    private String productStatus;
    private int productViewer;
    private int projectID;
    private int productTypeID;

}
