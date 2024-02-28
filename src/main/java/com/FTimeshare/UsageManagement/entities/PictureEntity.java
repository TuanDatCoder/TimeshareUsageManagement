package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="Picture")
public class PictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="img_id")
    private int imgID;

    @Column(name="img_name")
    private String imgName;

    @Lob
    @Column(name="img_data")
    private byte[] imgData;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private ProductEntity productID;


}
