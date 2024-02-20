package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/customerview")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/customer/createbooking")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto booking) {
        BookingDto createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
    @DeleteMapping("/customer/deletebooking/{bookingID}")
    public ResponseEntity<?> deleteBooking(@PathVariable int bookingID) {
        BookingDto deletedBooking = bookingService.deleteBooking(bookingID);

        if (deletedBooking != null) {
            return ResponseEntity.ok(deletedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
