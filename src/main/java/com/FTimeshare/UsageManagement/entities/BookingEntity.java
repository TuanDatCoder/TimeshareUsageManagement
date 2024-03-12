package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Data
@NoArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingID")
    private int bookingID;

    @Column(name = "start_date")  
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "booking_price")
    private float bookingPrice;

    @Column(name = "booking_person")
    private int bookingPerson;

    @Column(name = "booking_status")
    private String bookingStatus;

    @Column(name = "img_name")
    private String imgName;

    @Lob
    @Column(name = "img_data")
    private byte[] imgData;

    @Column(name = "img_respond_name")
    private String imgRespondName;

    @Lob
    @Column(name = "img_respond_data")
    private byte[] imgRespondData;

    @ManyToOne
    @JoinColumn(name = "acc_id", referencedColumnName = "acc_id")
    private AccountEntity accID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private ProductEntity productID;

}
