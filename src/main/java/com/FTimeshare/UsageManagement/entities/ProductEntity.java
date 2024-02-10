package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productID;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productDescription")
    private String productDescription;

    @Column(name = "productConvinience")
    private String productConvinience;

    @Column(name = "productArea")
    private float productArea;

    @Column(name = "productPrice")
    private float productPrice;

    @Column(name = "availableStartDate")
    private LocalDateTime availableStartDate;

    @Column(name = "availableEndDate")
    private LocalDateTime availableEndDate;

    @Column(name = "productStatus")
    private boolean productStatus;

    @Column(name = "productPicture")
    private String productPicture;

    @ManyToOne
    @JoinColumn(name = "projectID", referencedColumnName = "projectID")
    private ProjectEntity projectID;


    @ManyToOne
    @JoinColumn(name = "productTypeID", referencedColumnName = "productTypeID")
    private ProductTypeEntity productTypeID;
}
