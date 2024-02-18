package com.FTimeshare.UsageManagement.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTypeDto {
    private int productTypeID;
    private String productTypeName;
}
