package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Report", schema = "dbo", catalog = "master")
public class ReportEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rID", nullable = false, length = 20)
    private String rId;
    @Basic
    @Column(name = "rCreateDate", nullable = true)
    private Date rCreateDate;
    @Basic
    @Column(name = "rDetail", nullable = true, length = 300)
    private String rDetail;
    @Basic
    @Column(name = "rStatus", nullable = true)
    private Boolean rStatus;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;


}
