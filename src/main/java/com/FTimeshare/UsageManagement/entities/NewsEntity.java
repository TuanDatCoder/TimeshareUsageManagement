package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Entity
@Table(name = "News")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private int newsID;

    @Column(name = "news_title")
    private String newsTitle;

    @Column(name = "news_post")
    private LocalDateTime newsPost;

    @Column(name = "news_content")
    private String newsContent;

    @Column(name = "img_name")
    private String imgName;

    @Lob
    @Column(name = "img_data")
    private byte[] imgData;

    @Column(name = "news_viewer")
    private int newsViewer;

    @Column(name = "news_status")
    private String newsStatus;

    @ManyToOne
    @JoinColumn(name = "acc_id", referencedColumnName = "acc_id")
    private AccountEntity accID;


}
