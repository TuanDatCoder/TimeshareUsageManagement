package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "Feedback")
@Data
@NoArgsConstructor
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private String feedbackID;

    @Column(name = "feedback_create_date")
    private LocalDateTime feedbackCreateDate;

    @Column(name = "feedback_detail")
    private String feedbackDetail;

    @Column(name = "feedback_status")
    private boolean feedbackStatus;

    @OneToOne
    @JoinColumn(name = "bookingID", referencedColumnName = "bookingID")
    private BookingEntity bookingID;
}
