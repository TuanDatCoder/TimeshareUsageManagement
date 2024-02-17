package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.repositories.BookingRepository;
import com.FTimeshare.UsageManagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/ViewAll")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingEntity> bookings = bookingService.getAllBookings();
        List<BookingDto> bookingDtos = bookings.stream()
                .map(booking -> new BookingDto(booking.getBookingID(), booking.getStartDate(), booking.getEndDate(), booking.getBookingPrice(), booking.getUserID().getUserID(), booking.getProductID().getProductID()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(bookingDtos);
    }
}
