package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bookingID;

    @Column(name = "starDate")
    private LocalDateTime starDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;

    @Column(name = "bookingPrice")
    private float bookingPrice;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserEntity userID;

    @OneToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private ProductEntity productID;
}
