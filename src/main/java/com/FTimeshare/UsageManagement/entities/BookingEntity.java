package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Entity
@Table(name = "Booking")
@Data
@NoArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bookingID;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "bookingPrice")
    private float bookingPrice;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserEntity userID;

    @OneToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private ProductEntity productID;
}
