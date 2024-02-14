package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

//    private final BookingRepository bookingRepository;
//
//    @Autowired
//    public BookingController(BookingRepository bookingRepository) {
//        this.bookingRepository = bookingRepository;
//    }
//
//    @GetMapping
//    public List<BookingEntity> getAllBookings() {
//        return bookingRepository.findAll();
//    }

}

