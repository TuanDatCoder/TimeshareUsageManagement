package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    private int paymentID;

    @Column(name="account_name")
    private String accountName;

    @Column(name="banking")
    private String banking;

    @Column(name="account_number")
    private String accountNumber;

    @Lob
    @Column(name="image_banking",length = 1000)
    private byte[] imageBanking;

    @ManyToOne
    @JoinColumn(name = "acc_id", referencedColumnName = "acc_id")
    private  AccountEntity accId;
}
