package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.FeedbackDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.FeedbackEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.BookingRepository;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<BookingDto> getAllBookings() {
        List<BookingEntity> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(bookingEntity -> new BookingDto(
                        bookingEntity.getBookingID(),
                        bookingEntity.getStartDate(),
                        bookingEntity.getEndDate(),
                        bookingEntity.getBookingPrice(),
                        bookingEntity.getBookingStatus(),
                        bookingEntity.getAccID().getAccID(),
                        bookingEntity.getProductID().getProductID()))
                .collect(Collectors.toList());
    }

    @Override
    public BookingDto createBooking(BookingDto booking) {
        BookingEntity bookingEntity = new BookingEntity();
        // Set properties of bookingEntity from bookingRequest
        bookingEntity.setStartDate(booking.getStartDate());
        bookingEntity.setEndDate(booking.getEndDate());
        bookingEntity.setBookingPrice(booking.getBookingPrice());

        // Assuming you have UserRepository and ProductRepository
        AccountEntity accountEntity = accountRepository.findById(String.valueOf(booking.getAccID())).orElse(null);
        ProductEntity productEntity = productRepository.findById(String.valueOf(booking.getProductID())).orElse(null);

        if (accountEntity != null && productEntity != null) {
            bookingEntity.setAccID(accountEntity);
            bookingEntity.setProductID(productEntity);
            // Set other properties as needed

            // Save the bookingEntity
            BookingEntity savedBookingEntity = bookingRepository.save(bookingEntity);

            // Convert and return the saved entity as DTO
            return convertToDto(savedBookingEntity);
        } else {
            // Handle case where user or product is not found
            return null;
        }
    }

    private BookingDto convertToDto(BookingEntity bookingEntity) {
        // Your existing DTO conversion logic
        return new BookingDto(
                bookingEntity.getBookingID(),
                bookingEntity.getStartDate(),
                bookingEntity.getEndDate(),
                bookingEntity.getBookingPrice(),
                bookingEntity.getBookingStatus(),
                bookingEntity.getAccID().getAccID(),
                bookingEntity.getProductID().getProductID());
    }
}

