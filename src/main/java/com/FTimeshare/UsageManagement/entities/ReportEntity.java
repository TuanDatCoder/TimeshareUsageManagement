package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Report")
@Data
@NoArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    private int reportID;

    @Column(name = "report_create_date")
    private LocalDateTime reportCreateDate;

    @Column(name = "report_detail")
    private String reportDetail;

    @Column(name = "report_status")
    private String reportStatus;

    @ManyToOne
    @JoinColumn(name = "acc_id", referencedColumnName = "acc_id")
    private AccountEntity accID;

    @ManyToOne
    @JoinColumn(name="productID", referencedColumnName = "productID")
    private ProductEntity productID;

}
