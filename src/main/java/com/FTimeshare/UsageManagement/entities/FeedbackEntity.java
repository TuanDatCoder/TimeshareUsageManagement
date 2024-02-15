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
    private String feedbackID;

    @Column(name = "feedbackCreateDate")
    private LocalDateTime feedbackCreateDate;

    @Column(name = "feedbackDetail")
    private String feedbackDetail;

    @Column(name = "feedbackStatus")
    private boolean feedbackStatus;

    @OneToOne
    @JoinColumn(name = "bookingID", referencedColumnName = "bookingID")
    private BookingEntity bookingID;
}
