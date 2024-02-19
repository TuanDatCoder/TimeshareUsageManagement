package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Picture")
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
