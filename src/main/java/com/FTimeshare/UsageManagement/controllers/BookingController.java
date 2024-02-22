package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

//    @GetMapping("/ViewAll")
//    public ResponseEntity<List<BookingEntity>> getAllBookings() {
//        List<BookingEntity> bookings = bookingService.getAllBookings();
//        return ResponseEntity.ok(bookings);
//    }
}
