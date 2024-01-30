package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
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
    private Date newsPost;
    @Basic
    @Column(name = "newsContent", nullable = true, length = 600)
    private String newsContent;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public Date getNewsPost() {
        return newsPost;
    }

    public void setNewsPost(Date newsPost) {
        this.newsPost = newsPost;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
