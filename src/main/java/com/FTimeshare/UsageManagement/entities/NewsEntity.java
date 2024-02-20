package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "News")
@Data
@NoArgsConstructor
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

    @Column(name = "news_picture")
    private String newsPicture;

    @Column(name = "news_viewer")
    private int newsViewer;

    @Column(name = "news_status")
    private String newsStatus;

    @ManyToOne
    @JoinColumn(name = "acc_id", referencedColumnName = "acc_id")
    private AccountEntity accID;


}
