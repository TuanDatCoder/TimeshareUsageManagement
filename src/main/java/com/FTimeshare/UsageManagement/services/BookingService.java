package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;

public interface BookingService  {
    String createBooking(BookingDto bookingDto);
    String reserveRoom(BookingDto bookingDto);

    BookingDto getBookingById(String bookingId);

    boolean updateBooking(String bookingId, BookingDto bookingDto);

    boolean deleteBooking(String bookingId);
}
