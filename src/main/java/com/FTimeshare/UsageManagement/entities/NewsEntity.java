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
    private String newsID;
    @Column(name = "newsTitle")
    private String newsTitle;

    @Column(name = "newsPost")
    private LocalDateTime newsPost;

    @Column(name = "newsContent")
    private String newsContent;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private UserEntity Userid;
}
