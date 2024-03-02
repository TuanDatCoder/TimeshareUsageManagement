package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/mailbox")
public class MailboxController {
    //@Autowired

   // @PostMapping("/customer/submitfeedback")
 //   public ResponseEntity<FeedbackDto> submitFeedback(@RequestBody FeedbackDto feedbackDto) {
//        LocalDateTime now = LocalDateTime.now();
//        feedbackDto.setFeedbackCreateDate(now);
//
//        FeedbackDto submittedFeedback = feedbackService.submitFeedback(feedbackDto);
//        return new ResponseEntity<>(submittedFeedback, HttpStatus.CREATED);
   // }
}
