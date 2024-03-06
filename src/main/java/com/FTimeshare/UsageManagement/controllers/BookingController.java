package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
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

    //Khach hang create booking, booking Entity duoc tao ra voi status Wait to confirm
    @PostMapping("/customer/createbooking")
    public ResponseEntity<?> createBooking(@RequestParam("bill") MultipartFile file,
                                           @RequestParam String startDate,
                                           @RequestParam String endDate,
                                           @RequestParam int booking_person,
                                           @RequestParam int acc_id,
                                           @RequestParam int productID) throws IOException {
        LocalDateTime start_date = LocalDateTime.parse(startDate);
        LocalDateTime end_date = LocalDateTime.parse(endDate);
        Duration duration = Duration.between(start_date, end_date);
        long hours = duration.toHours();
        if (hours < 24 ){
            return new ResponseEntity<>("Your check-in date is too close, please choose your check-in date at least 24 hours from now", HttpStatus.NOT_ACCEPTABLE);
        }
        BookingDto booking = BookingDto.builder().startDate(start_date)
                                                 .endDate(end_date)
                                                 .bookingPerson(booking_person)
                                                 .accID(acc_id)
                                                 .productID(productID)
                                                 .build();
        if (bookingService.checkBooking(booking) == false){
            return new ResponseEntity<>("We're so sorry! Our timeshare is busy this time", HttpStatus.NOT_ACCEPTABLE);
        }
        BookingDto createdBooking = bookingService.createBooking(booking, file);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
    //Api cancel, nếu status là wait to confirm thì đổi thành wait to confirm(request cancel)
    //nếu status là Active thì đổi thành wait to respond + x
    @PostMapping("/cancel/{bookingID}")
    public ResponseEntity<?> cancelBookingV2(@PathVariable int bookingID){
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        if(booking.getBookingStatus().equals("Wait to confirm")){
            bookingService.statusBooking(bookingID,"Wait to confirm (request cancel)");
            return ResponseEntity.ok("Submit cancel request");
        } else if(booking.getBookingStatus().equals("Active")) {
            LocalDateTime current = LocalDateTime.now();
            if(current.isAfter(booking.getStartDate())){
                return ResponseEntity.ok("You can't cancel booking after check-in date");
            }
            Duration duration = Duration.between(current, booking.getStartDate());
            long days = duration.toHours();
            if (days >= 24) {
                bookingService.statusBooking(bookingID, "Waiting respond payment (100%)");
            } else {
                bookingService.statusBooking(bookingID, "Waiting respond payment (80%)");
            }
        }
        return ResponseEntity.ok("Submit cancel request");
    }

    //staff check chuyen khoan va confirm
    @PutMapping("/confirm_booking/{bookingID}")
    public ResponseEntity<String> confirmBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Active");
        return ResponseEntity.ok("Done");
    }




    //staff đã chuyển tiền và xác nhận
    @PutMapping("/confirm_booking_respond_payment/{bookingID}")
    public ResponseEntity<String> confirmBookingRespondPayment(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Canceled");
        return ResponseEntity.ok("Done");
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


    @GetMapping("/view-booking-by-status/{status}")
    public ResponseEntity<List<BookingDto>> getStatusBooking(@PathVariable String status) {
        List<BookingEntity> statusProducts = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(convertToDtoList(statusProducts));
    }

//    public ResponseEntity<List<BookingEntity>> getStatusBookingEntity(String status) {
//        List<BookingDto> statusBookings = bookingService.getBookingsByStatus(status);
//        List<BookingEntity> bookingEntities = convertToEntityList(statusBookings);
//        return ResponseEntity.ok(bookingEntities);
//    }

    // View total status
    @GetMapping("staff/totalPending")
//    public long countPendingBookings() {
//        ResponseEntity<List<BookingEntity>> responseEntity = getStatusBookingEntity("Pending");
//        List<BookingEntity> pendingBookings = responseEntity.getBody();
//        return pendingBookings.size();
//    }

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
    public BookingDto convertToDto(BookingEntity bookingEntity) {
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

    private List<BookingEntity> convertToEntityList(List<BookingDto> bookingDtos) {
        return bookingDtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    private BookingEntity convertToEntity(BookingDto bookingDto) {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBookingID(bookingDto.getBookingID());
        bookingEntity.setStartDate(bookingDto.getStartDate());
        bookingEntity.setEndDate(bookingDto.getEndDate());
        bookingEntity.setBookingPrice(bookingDto.getBookingPrice());
        bookingEntity.setBookingPerson(bookingDto.getBookingPerson());
        bookingEntity.setBookingStatus(bookingDto.getBookingStatus());
        bookingEntity.setImgName(bookingDto.getImgName());
        bookingEntity.setImgData(bookingDto.getImgData());

        // Assuming that 'AccID' and 'ProductID' are references to other entities
        // Set references to other entities based on their IDs
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccID(bookingDto.getAccID());
        bookingEntity.setAccID(accountEntity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductID(bookingDto.getProductID());
        bookingEntity.setProductID(productEntity);

        return bookingEntity;
    }


}