package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.ProductDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
                bookingService.statusBooking(bookingID, "Wait to respond payment (100%)");
            } else {
                bookingService.statusBooking(bookingID, "Wait to respond payment (80%)");
            }
        }
        return ResponseEntity.ok("Submit cancel request");
    }

    @GetMapping("imgView/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=bookingService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    //chu nha check chuyen khoan va confirm
    @PutMapping("/confirm_booking/{bookingID}")
    public ResponseEntity<String> confirmBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Active");
        return ResponseEntity.ok("Done");
    }

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

    @GetMapping("/view-booking-by-2status/{status}")
    public ResponseEntity<List<BookingDto>> getStatusBooking2( String status1, String status2) {
        List<BookingEntity> statusBooking = bookingService.getBookingsByStatus2(status1, status2);
        return ResponseEntity.ok(convertToDtoList(statusBooking));
    }

    // View total status
    @GetMapping("staff/totalPending")
        public int countPendingBookings() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Pending");
        List<BookingDto> pendindBooking = responseEntity.getBody();
        return pendindBooking.size();
    }
    @GetMapping("staff/totalWaitToConfirm")
    public int countWaitToConfirmBookings() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Wait To Confirm");
        List<BookingDto> waitBooking = responseEntity.getBody();
        return waitBooking.size();
    }
    @GetMapping("staff/totalWaitToConfirmRC")
    public int countWaitToConfirmRC() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Wait to confirm (request cancel)");
        List<BookingDto> waitBooking = responseEntity.getBody();
        return waitBooking.size();
    }
    @GetMapping("staff/totalWaitToRespond")
    public long countWaitToRespondBookings() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Wait To Respond");
        List<BookingDto> respondBookings = responseEntity.getBody();
        return respondBookings.size();
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
    @PutMapping("staff/respond80/{bookingID}")
    public ResponseEntity<String> respondBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Wait To Respond (80%)");
        return ResponseEntity.ok("Active");
    }

    @PutMapping("staff/respond100/{bookingID}")
    public ResponseEntity<String> respondBooking2(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Wait To Respond (100%)");
        return ResponseEntity.ok("Active");
    }

    @PutMapping("staff/finalcancel80/{bookingID}")
    public ResponseEntity<String> finalcancelBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Cancelled");
        return ResponseEntity.ok("Wait To Respond (80%)");
    }

    @PutMapping("staff/finalcancel100/{bookingID}")
    public ResponseEntity<String> finalcancelBooking2(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Cancelled");
        return ResponseEntity.ok("Wait To Respond (100%)");
    }

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

    @PutMapping("staff/waitToConfirm/{bookingID}")
    public ResponseEntity<String> waitToConfirm(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Wait To Confirm");
        return ResponseEntity.ok("Done");
    }

    @PutMapping("staff/waitToConfirmRequestCancel/{bookingID}")
    public ResponseEntity<String> waitToConfirmRequestCancel(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Wait to confirm (request cancel)");
        return ResponseEntity.ok("Done");
    }
    @PutMapping("staff/Rejected/{bookingID}")
    public ResponseEntity<String> rejectedBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Rejected");
        return ResponseEntity.ok("Done");
    }

    // view theo status
    //View wait to confirm - wait to confirm cancel
    public ResponseEntity<List<BookingDto>> getStatusBookingAcc(int accID, String status1, String status2) {
        List<BookingEntity> statusProducts = bookingService.getBookingsByStatusByAccount(accID,status1,status2);
        return ResponseEntity.ok(convertToDtoList(statusProducts));
    }
    @GetMapping("customer/waitToByAccId/{accID}")
    public ResponseEntity<List<BookingDto>> getWaitToConfirm(@PathVariable int accID) {
        return getStatusBookingAcc(accID,"Wait To Confirm", "Wait to confirm (request cancel)");
    }


    @GetMapping("staff/waitToConfirm")
    public ResponseEntity<List<BookingDto>> getWaitToBooking() {
        return getStatusBooking("Wait To Confirm");
    }

    @GetMapping("staff/waitToConfirmRC")
    public ResponseEntity<List<BookingDto>> getWaitToRCBooking() {
        return getStatusBooking("Wait to confirm (request cancel)");
    }


    @PutMapping("staff/Done/{bookingID}")
    public ResponseEntity<String> doneBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Done");
        return ResponseEntity.ok("Done");
    }

    // view theo status

    @GetMapping("staff/waitToRespond-Active")
    public ResponseEntity<List<BookingDto>> getWaitToRespondBooking() { return getStatusBooking2("Wait to respond", "Active");}
    @GetMapping("staff/WaitRespondPayment(100)")
    public ResponseEntity<List<BookingDto>> getWaitRespond100() {
        return getStatusBooking("Wait to respond payment (100%)");
    }
    @GetMapping("staff/WaitRespondPayment(80)")
    public ResponseEntity<List<BookingDto>> getWaitRespond80() {
        return getStatusBooking("Wait to respond payment (80%)");
    }
    @GetMapping("staff/respond")
    public ResponseEntity<List<BookingDto>> getRespondBooking() {
        return getStatusBooking("Wait To Respond");
    }
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
                "http://localhost:8080/api/payment/viewImg/" + bookingEntity.getImgName(),  // Thêm imgName vào đường dẫn
                new byte[0],
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

        bookingEntity.setImgName("http://localhost:8080/api/payment/viewImg/" + bookingEntity.getImgName());
        bookingEntity.setImgData(new byte[0]);

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