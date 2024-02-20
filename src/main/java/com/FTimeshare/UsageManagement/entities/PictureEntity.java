package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;

public class PictureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="img_id")
    private int img_id;

    @Column(name="img_name")
    private String img_name;

    @Column(name="img_url")
    private String img_url;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private ProductEntity productID;
}
