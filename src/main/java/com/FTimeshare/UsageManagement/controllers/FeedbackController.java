package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import com.FTimeshare.UsageManagement.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/customer/viewfeedback")
    public ResponseEntity<List<FeedbackDto>> getAllFeedback() {
        List<FeedbackDto> feedback = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedback);
    }

    @PostMapping("/customer/submitfeedback")
    public ResponseEntity<FeedbackDto> submitFeedback(@RequestBody FeedbackDto feedbackDto) {
        FeedbackDto submittedFeedback = feedbackService.submitFeedback(feedbackDto);
        return new ResponseEntity<>(submittedFeedback, HttpStatus.CREATED);
    }

    @PutMapping("/api/feedbacks/edit/{feedbackID}")
    public ResponseEntity<?> editFeedback(@PathVariable int feedbackID, @RequestBody FeedbackDto updatedFeedback) {
        FeedbackDto editedFeedback = feedbackService.editFeedback(feedbackID, updatedFeedback);
        return ResponseEntity.ok(editedFeedback);
    }


}
