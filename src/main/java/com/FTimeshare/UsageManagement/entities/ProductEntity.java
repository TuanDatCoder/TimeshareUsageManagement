package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private int productID;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_convenience")
    private String productConvenience;

    @Column(name = "product_area")
    private float productArea;

    @Column(name = "product_price")
    private float productPrice;

    @Column(name = "available_start_date")
    private LocalDateTime availableStartDate;

    @Column(name = "available_end_date")
    private LocalDateTime availableEndDate;

    @Column(name = "product_status")
    private String productStatus;

    @Column(name = "product_picture")
    private String productPicture;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private ProjectEntity projectID;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="user_id")
    private UserEntity userID;
    @ManyToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id")
    private ProductTypeEntity productTypeID;
}
