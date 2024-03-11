package com.FTimeshare.UsageManagement.controllers;
import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import com.FTimeshare.UsageManagement.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("http://localhost:5173")
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
        LocalDateTime now = LocalDateTime.now();
        feedbackDto.setFeedbackCreateDate(now);
        FeedbackDto submittedFeedback = feedbackService.submitFeedback(feedbackDto);
        return new ResponseEntity<>(submittedFeedback, HttpStatus.CREATED);
    }

    @PutMapping("/api/feedbacks/edit/{feedbackID}")
    public ResponseEntity<?> editFeedback(@PathVariable int feedbackID, @RequestBody FeedbackDto updatedFeedback) {
        LocalDateTime now = LocalDateTime.now();
        updatedFeedback.setFeedbackCreateDate(now);
        FeedbackDto editedFeedback = feedbackService.editFeedback(feedbackID, updatedFeedback);
        return ResponseEntity.ok(editedFeedback);
    }

    @DeleteMapping("/delete-feedback/{feedbackID}")
    public ResponseEntity<?> deleteFeedback(@PathVariable int feedbackID) {
        FeedbackDto deletedFeedback = feedbackService.deleteFeedback(feedbackID);

        if (deletedFeedback != null) {
            return ResponseEntity.ok(deletedFeedback);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/view-feedback-rating/{productID}")
    public ResponseEntity<List<Float>> viewFeedbackRatingByProductId(@PathVariable int productID) {
        List<Float> feedbackRatings = feedbackService.getFeedbackRatingByProductId(productID);
        return new ResponseEntity<>(feedbackRatings, HttpStatus.OK);
    }
    @GetMapping("/average-feedback-rating/{productID}")
    public ResponseEntity<Float> getAverageFeedbackRatingByProductId(@PathVariable int productID) {
        Float totalFeedbackRatings = feedbackService.getAverageFeedbackRatingByProductId(productID);
        return new ResponseEntity<>(totalFeedbackRatings, HttpStatus.OK);
    }

    @GetMapping("/viewByProductId/{productid}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByProductId(@PathVariable int productid) {
        List<FeedbackDto> feedback = feedbackService.viewFeedbackByProductID(productid);
        return ResponseEntity.ok(feedback);
    }
}
