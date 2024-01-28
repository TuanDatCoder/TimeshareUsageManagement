package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "productStatus")
    private boolean productStatus;

    @Column(name = "productPicture")
    private String productPicture;

    @ManyToOne
    @JoinColumn(name = "projectID", referencedColumnName = "projectID")
    private ProjectEntity projectID;


    @ManyToOne
    @JoinColumn(name = "productTypeID", referencedColumnName = "productTypeID")
    private ProductEntity productTypeID;
}
