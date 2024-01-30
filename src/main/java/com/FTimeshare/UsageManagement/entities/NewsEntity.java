package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Entity
@Table(name = "News")
@Data
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String NewsID;
    @Column(name = "newsTitle")
    private String newsTitle;

    @Column(name = "newsPost")
    private Date newsPost;

    @Column(name = "newsAuthor")
    private String newsAuthor;

    @Column(name = "newsContent")
    private String newsContent;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserEntity Userid;
}
