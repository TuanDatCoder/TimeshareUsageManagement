package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product_Type")
@Data
@NoArgsConstructor
public class ProductTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productTypeID;

    @Column(name = "productTypeName")
    private String productTypeName;
}
