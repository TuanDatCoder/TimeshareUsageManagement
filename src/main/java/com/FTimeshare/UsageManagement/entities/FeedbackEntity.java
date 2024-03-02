package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Feedback")
@Data
@NoArgsConstructor
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackID;

    @Column(name = "feedback_create_date")
    private LocalDateTime feedbackCreateDate = LocalDateTime.now() ;

    @Column(name = "feedback_detail")
    private String feedbackDetail;

    @Column(name = "feedback_status")
    private String feedbackStatus;

    @Column(name = "feedback_rating")
    private float feedbackRating;

    @OneToOne
    @JoinColumn(name = "bookingID", referencedColumnName = "bookingID")
    private BookingEntity bookingID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private ProductEntity productID;
}