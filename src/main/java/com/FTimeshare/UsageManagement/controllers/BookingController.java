package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/view-booking-by-Id/{bookingID}")
    public ResponseEntity<List<BookingDto>> viewBookingsByBookingId(@PathVariable int bookingID) {
        List<BookingDto> bookings = bookingService.getBookingsByBookingId(bookingID);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


    @GetMapping("/by-account/{accID}")
    public ResponseEntity<List<BookingDto>> viewBookingsByAccountId(@PathVariable int accID) {
        List<BookingDto> bookings = bookingService.getBookingsByAccountId(accID);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
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

    @GetMapping("/view-booking-price/{productID}")
    public ResponseEntity<List<Float>> viewBookingPricesByProductId(@PathVariable int productID) {
        List<Float> bookingPrices = bookingService.getBookingPricesByProductId(productID);
        return new ResponseEntity<>(bookingPrices, HttpStatus.OK);
    }
    @GetMapping("/total-booking-price/{productID}")
    public ResponseEntity<Float> getTotalBookingPriceByProductId(@PathVariable int productID) {
        Float totalBookingPrice = bookingService.getTotalBookingPriceByProductId(productID);
        return new ResponseEntity<>(totalBookingPrice, HttpStatus.OK);
    }
}