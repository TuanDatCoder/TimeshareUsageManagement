package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Entity
@Table(name = "Report")
@Data
@NoArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reportID;

    @Column(name = "reportCreateDate")
    private Date reportCreateDate;

    @Column(name = "reportDetail")
    private String reportDetail;

    @Column(name = "reportStatus")
    private String reportStatus;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserEntity userID;

}
