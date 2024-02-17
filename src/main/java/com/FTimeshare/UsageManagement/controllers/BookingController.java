package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody(required = false) BookingDto bookingDto) {
        String bookingId = bookingService.createBooking(bookingDto);
        return new ResponseEntity<>(bookingId, HttpStatus.CREATED);
    }

    @GetMapping("/{bookingId}") // Sửa đổi đường dẫn để sử dụng biến đường dẫn bookingId
    public ResponseEntity<BookingDto> getBooking(@PathVariable String bookingId) {
        BookingDto booking = bookingService.getBookingById(bookingId);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/booking/update/{bookingId}") // Thêm biến đường dẫn bookingId vào URL
    public ResponseEntity<Void> updateBooking(@PathVariable String bookingId, @RequestBody BookingDto bookingDto) {
        boolean updated = bookingService.updateBooking(bookingId, bookingDto);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/booking/delete/{bookingId}") // Thêm biến đường dẫn bookingId vào URL
    public ResponseEntity<Void> deleteBooking(@PathVariable String bookingId) {
        boolean deleted = bookingService.deleteBooking(bookingId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}