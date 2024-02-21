package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingDto> getAllBookings();


    BookingDto createBooking(BookingDto booking);

    BookingDto deleteBooking(int bookingId);

    List<BookingDto> getBookingsByAccountId(int accID);


    List<BookingDto> getBookingsByBookingId(int bookingID);
}

