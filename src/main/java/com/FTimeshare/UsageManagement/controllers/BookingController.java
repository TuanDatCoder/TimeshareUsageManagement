package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
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
    public ResponseEntity<?> createBooking(@RequestBody BookingDto booking) {
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
    //Staff duyet va view status

    public ResponseEntity<List<BookingDto>> getStatusBooking(String status) {
        List<BookingEntity> statusProducts = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(convertToDtoList(statusProducts));
    }

    // View total status
    @GetMapping("staff/totalPending")
    public int countPendingBookings() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Pending");
        List<BookingDto> pendindBooking = responseEntity.getBody();
        return pendindBooking.size();
    }
    @GetMapping("staff/totalActive")
    public int countActiveBookings() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Active");
        List<BookingDto> activeBooking = responseEntity.getBody();
        return activeBooking.size();
    }
    @GetMapping("staff/totalCancel")
    public int countCancelBookings() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Cancel");
        List<BookingDto> cancelBooking = responseEntity.getBody();
        return cancelBooking.size();
    }
    @GetMapping("staff/totalDone")
    public int countDoneBookings() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Done");
        List<BookingDto>doneBooking = responseEntity.getBody();
        return doneBooking.size();
    }

// làm change status
    @PutMapping("staff/active/{bookingID}")
    public ResponseEntity<String> activeBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Active");
        return ResponseEntity.ok("Done");
    }
    @PutMapping("staff/cancel/{bookingID}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Cancel");
        return ResponseEntity.ok("Done");
    }

    @PutMapping("staff/pending/{bookingID}")
    public ResponseEntity<String> pendingBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Pending");
        return ResponseEntity.ok("Done");
    }

    @PutMapping("staff/Done/{bookingID}")
    public ResponseEntity<String> doneBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Done");
        return ResponseEntity.ok("Done");
    }

    // view theo status

    @GetMapping("staff/active")
    public ResponseEntity<List<BookingDto>> getActiveBooking() {
        return getStatusBooking("Active");
    }
    @GetMapping("staff/pending")
    public ResponseEntity<List<BookingDto>> getPendingBooking() {
        return getStatusBooking("Pending");
    }
    @GetMapping("staff/cancel")
    public ResponseEntity<List<BookingDto>> getCancelBooking() {
        return getStatusBooking("Cancel");
    }
    @GetMapping("staff/done")
    public ResponseEntity<List<BookingDto>> getDoneBooking() {
        return getStatusBooking("Done");
    }

    private List<BookingDto> convertToDtoList(List<BookingEntity> bookingEntities) {
        return bookingEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private BookingDto convertToDto(BookingEntity bookingEntity) {
        // Your existing DTO conversion logic
        return new BookingDto(
                bookingEntity.getBookingID(),
                bookingEntity.getStartDate(),
                bookingEntity.getEndDate(),
                bookingEntity.getBookingPrice(),
                bookingEntity.getBookingPerson(),
                bookingEntity.getBookingStatus(),
                bookingEntity.getImgName(),
                bookingEntity.getImgData(),
                bookingEntity.getAccID().getAccID(),
                bookingEntity.getProductID().getProductID());
    }

}