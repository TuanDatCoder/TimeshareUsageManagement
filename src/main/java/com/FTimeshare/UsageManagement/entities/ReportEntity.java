package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
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

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public Date getrCreateDate() {
        return rCreateDate;
    }

    public void setrCreateDate(Date rCreateDate) {
        this.rCreateDate = rCreateDate;
    }

    public String getrDetail() {
        return rDetail;
    }

    public void setrDetail(String rDetail) {
        this.rDetail = rDetail;
    }

    public Boolean getrStatus() {
        return rStatus;
    }

    public void setrStatus(Boolean rStatus) {
        this.rStatus = rStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
