package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.repositories.BookingRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service

public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String createBooking(BookingDto bookingDto) {
        BookingEntity bookingEntity = mapToBookingEntity(bookingDto);
    try {
        BookingEntity savedBooking = bookingRepository.save(bookingEntity);
        return savedBooking.getBookingID();
    } catch (Exception e) {
        // Handle the exception or throw a more specific exception
        throw new IllegalStateException("Failed to create booking", e);
    }
}

    @Override
    public String reserveRoom(BookingDto bookingDto) {
        return null;
    }

    @Override
    public BookingDto getBookingById(String bookingId) {
        return null;
    }

    @Override
    public boolean updateBooking(String bookingId, BookingDto bookingDto) {
        return false;
    }

    @Override
    public boolean deleteBooking(String bookingId) {
        return false;
    }

    private BookingEntity mapToBookingEntity(BookingDto bookingDto) {
        if (bookingDto == null) {
            // Xử lý lỗi hoặc ném ngoại lệ tại đây
            throw new IllegalArgumentException("bookingDto cannot be null");
        }
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBookingID(bookingDto.getBookingID());
        bookingEntity.setStartDate(bookingDto.getStartDate());
        bookingEntity.setEndDate(bookingDto.getEndDate());
        bookingEntity.setBookingPrice(bookingDto.getBookingPrice());
        return bookingEntity;
    }
}