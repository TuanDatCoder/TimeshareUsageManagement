package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "News", schema = "dbo", catalog = "master")
public class NewsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "newsID", nullable = false, length = 20)
    private String newsId;
    @Basic
    @Column(name = "newsTitle", nullable = true, length = 20)
    private String newsTitle;
    @Basic
    @Column(name = "newsPost", nullable = true)
    private LocalDateTime newsPost;
    @Basic
    @Column(name = "newsContent", nullable = true, length = 600)
    private String newsContent;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;


}
