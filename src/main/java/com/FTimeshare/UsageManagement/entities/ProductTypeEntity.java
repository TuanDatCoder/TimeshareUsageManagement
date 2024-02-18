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
    @Column(name = "product_type_id")
    private int productTypeID;

    @Column(name = "product_type_name")
    private String productTypeName;
}
