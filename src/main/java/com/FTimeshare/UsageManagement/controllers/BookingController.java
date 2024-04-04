package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.config.Config;
import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.BookingRepository;
import com.FTimeshare.UsageManagement.services.AccountService;
import com.FTimeshare.UsageManagement.services.BookingService;
import com.FTimeshare.UsageManagement.services.ProductService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin("http://localhost:5173")
@CrossOrigin(origins = "https://pass-timeshare.vercel.app")
//@CrossOrigin(origins = "https://pass-timeshare-tuandat-frontends-projects.vercel.app")
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountController accountController;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductController productController;


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

    @GetMapping("/by-account/done/{accID}")
    public ResponseEntity<List<BookingDto>> viewBookingsByAccountId_Done(@PathVariable int accID) {
        List<BookingDto> bookings = bookingService.getBookingsByAccountId_Status(accID, "Done");
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/by-account/cancel/{accID}")
    public ResponseEntity<List<BookingDto>> viewBookingsByAccountId_Cancel(@PathVariable int accID) {
        List<BookingDto> bookings = bookingService.getBookingsByAccountId_Status(accID, "Cancelled");
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }




    @PostMapping("/customer/checkbooking")
    public ResponseEntity<?> checkbooking(@RequestParam String startDate,
                                          @RequestParam String endDate,
                                          @RequestParam int productID,
                                          @RequestParam int booking_person) throws IOException{
        LocalDateTime start_date = LocalDateTime.parse(startDate);
        LocalDateTime end_date = LocalDateTime.parse(endDate);
        LocalDateTime localDateTime = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, start_date);
        ProductEntity chosenTimeShare = productService.getProductById(productID);
        long hours = duration.toHours();

        if(start_date.isAfter(end_date))
            return new ResponseEntity<>("Sorry, your start date must before end date", HttpStatus.NOT_ACCEPTABLE);

        if(start_date.isBefore(localDateTime)||end_date.isBefore(localDateTime))
            return new ResponseEntity<>("Sorry, your chosen start date has been passed", HttpStatus.NOT_ACCEPTABLE);

        if(!(start_date.isAfter(chosenTimeShare.getAvailableStartDate())&&start_date.isBefore(chosenTimeShare.getAvailableEndDate())
            &&end_date.isAfter(chosenTimeShare.getAvailableStartDate())&&end_date.isBefore(chosenTimeShare.getAvailableEndDate())))
            return new ResponseEntity<>("Sorry, our timeshare is unavailable in this time", HttpStatus.NOT_ACCEPTABLE);

        if (hours < 24 )
            return new ResponseEntity<>("Your check-in date is too close, please choose your check-in date at least 24 hours from now", HttpStatus.NOT_ACCEPTABLE);

        if (!bookingService.checkBookingByDateAndProductID(start_date, end_date, productID))
            return new ResponseEntity<>("We're so sorry! Our timeshare is busy this time", HttpStatus.NOT_ACCEPTABLE);

        if (booking_person > chosenTimeShare.getProductPerson())
            return new ResponseEntity<>("Sorry! The maximum number of persons available for this timeshare is " + productService.getProductPersonByProductId(productID), HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Your booking is acceptable", HttpStatus.ACCEPTED);
    }

    @PostMapping("/customer/checkbooking_person")
    public ResponseEntity<?> checkbooking(@RequestParam int productID,
                                          @RequestParam int booking_person) throws IOException{
        ProductEntity chosenTimeShare = productService.getProductById(productID);
        if (booking_person > chosenTimeShare.getProductPerson())
            return new ResponseEntity<>("Sorry! The maximum number of persons available for this timeshare is " + productService.getProductPersonByProductId(productID), HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Your booking is acceptable", HttpStatus.ACCEPTED);
    }
    //Khach hang create booking, booking Entity duoc tao ra voi status Wait to confirm
    @PostMapping("/customer/createbooking")
    public String createBooking(@RequestParam("bill") MultipartFile file,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam int booking_person,
                                @RequestParam int acc_id,
                                @RequestParam int productID) throws IOException {
        LocalDateTime start_date = LocalDateTime.parse(startDate);
        LocalDateTime end_date = LocalDateTime.parse(endDate);

        BookingDto booking = BookingDto.builder().startDate(start_date)
                                                 .endDate(end_date)
                                                 .bookingPerson(booking_person)
                                                 .accID(acc_id)
                                                 .productID(productID)
                                                 .build();

        BookingDto createdBooking = bookingService.createBooking(booking, file);

        return getPay((long) createdBooking.getBookingPrice(), createdBooking.getBookingID());
    }

    @PostMapping("/pay")
    public String getPay(@RequestParam long amountPaymemnt, @RequestParam int bookingID) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = amountPaymemnt*100;
        String bankCode = "NCB";

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vi_VN");
        vnp_Params.put("vnp_ReturnUrl", returnWebAfterPayment(bookingID));

        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

//        LocalDateTime currentDateTime = LocalDateTime.now();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//
//        String vnp_CreateDate = currentDateTime.format(formatter);
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//        LocalDateTime expireDateTime = currentDateTime.plusMinutes(15);
//        String vnp_ExpireDate = expireDateTime.format(formatter);
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnp_CreateDate = currentDateTime.format(formatter);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        ZonedDateTime expireDateTime = currentDateTime.plusMinutes(10);
        String vnp_ExpireDate = expireDateTime.format(formatter);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;

    }
    @PostMapping  ("/returnWebAfterPayment")
    public String returnWebAfterPayment(@RequestParam int bookingID){
        return "http://localhost:5173/confirm-success-payment/"+ bookingID;
    }

    @PostMapping ("/sendWebAfterPayment")
    public void sendWebAfterPayment(@RequestParam int bookingID, @RequestParam int type){
        if(type == 0) sendBookingEmail(bookingID,"You have successfully booked the product: ","Thank you for your reservation at " );
        else{
            confirmBookingRespondPayment(bookingID);
        }
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

    //upload respond
//    @PutMapping("/updateImgRespond/{bookingID}")
//    public ResponseEntity<?> updateImageRespond(@PathVariable int bookingID, @RequestParam("picture") MultipartFile file) {
//
//        try {
//            ResponseEntity<?> response = bookingService.updateImage(file, bookingID);
//            return response;
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(e.getMessage());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating image: " + e.getMessage());
//        }
//    }


    //View anh
//    @GetMapping("/paymentRespond/viewImg/{fileName}")
//    public ResponseEntity<?> downloadImageRespond(@PathVariable String fileName){
//        byte[] imageRespondData=bookingService.downloadImageRespond(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageRespondData);
//    }
//    @GetMapping("/viewImg/{fileName}")
//    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
//        byte[] imageData=bookingService.downloadImage(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//    }



    //chu nha check chuyen khoan va confirm
    @PutMapping("/confirm_booking/{bookingID}")
    public ResponseEntity<String> confirmBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Active");
        return ResponseEntity.ok("Done");
    }

    @PutMapping("/confirm_booking_respond_payment/{bookingID}")
    public ResponseEntity<String> confirmBookingRespondPayment(@PathVariable int bookingID) {
        sendCancelEmail(bookingID);
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        if(booking.getBookingStatus().equals("Wait to respond payment (80%)")) booking.setBookingPrice(booking.getBookingPrice()*0.2f);;
        booking.setBookingPrice(0f);
        bookingService.statusBooking(bookingID,"Cancelled");

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
    @GetMapping("/view-booking-by-2statusByAccID/")
    public ResponseEntity<List<BookingDto>> getBookingStatusAccID( String status1, String status2, int accID) {
        List<BookingEntity> statusBooking = bookingService.getBookingStatusAndAccID(status1, status2, accID);
        return ResponseEntity.ok(convertToDtoList(statusBooking));
    }

//    @GetMapping("staff/DoneCancelled/{accID}")
//    public List<BookingDto> getDoneCancelledByOwner(@PathVariable int accID) {
//        ResponseEntity<List<BookingDto>> listBooking = getBookingStatusAccID("Done", "Cancelled", accID);
//        List<BookingDto> bookingList = listBooking.getBody();
//
//        return bookingList;
//    }
    @GetMapping("staff/DoneCancelled/{accID}")
    public List<BookingDto> getDoneCancelledByOwner(@PathVariable int accID) {
        ResponseEntity<List<BookingDto>> listBooking = getStatusBooking2("Done", "Cancelled");
        List<BookingDto> bookingList = listBooking.getBody();
        List<BookingDto> ownerBookingList = new ArrayList<>();
        if (bookingList != null) {
            for (BookingDto booking : bookingList) {
                ProductEntity product = productService.getProductById(booking.getProductID());
                if (product.getAccID().getAccID() == accID) ownerBookingList.add(booking);
            }
        }

        return ownerBookingList;
    }
    @GetMapping("staff/TotalOwnerDoneCancelled/{accID}")
    public float getTotalOwnerDoneCancelled(@PathVariable int accID) {

        List<BookingDto> bookingList = getDoneCancelledByOwner(accID);
        float totalBookingOwner = 0f;
        if (bookingList != null) {
            for (BookingDto booking : bookingList) {
                if(booking.getBookingStatus().equals("Done")){
                    totalBookingOwner += booking.getBookingPrice() * 0.9f;
                }
                totalBookingOwner += booking.getBookingPrice();
            }
        }
        return totalBookingOwner;
    }

    @GetMapping("/admin/total_Price_For_Done_Bookings")
    public float totalPriceForDoneBookings() {
        List<BookingEntity> doneBookings = bookingService.getBookingsByStatus("Done");
        float totalBookingPrice = 0;
        for (BookingEntity b: doneBookings) {
            totalBookingPrice += b.getBookingPrice();
        }
        return totalBookingPrice*0.1f;
    }

    @GetMapping("/admin/monthlyTotalPrice/{year}")
    public Map<Integer, Float> calculateMonthlyTotalPrice(@PathVariable int year) {
        List<BookingEntity> doneBookings = bookingService.getBookingsByStatus("Done");
        Map<Integer, Float> monthlyTotalPriceMap = new HashMap<>();

        for (BookingEntity booking : doneBookings) {
           if(booking.getCreateDate().getYear() == year){
               int month = booking.getCreateDate().getMonthValue();
               float totalPrice = booking.getBookingPrice() * 0.1f;
               monthlyTotalPriceMap.put(month, monthlyTotalPriceMap.getOrDefault(month, 0f) + totalPrice);
           }
        }
        return monthlyTotalPriceMap;
    }

    @GetMapping("/admin/yearlyTotalPrice/{year}")
    public Float calculateYearlyTotalPrice(@PathVariable int year) {
        Map<Integer, Float> monthlyTotalPriceMap = calculateMonthlyTotalPrice(year);
        float yearlyTotalPrice = 0;
        for (float price : monthlyTotalPriceMap.values()) {
            yearlyTotalPrice += price;
        }
        return yearlyTotalPrice;
    }

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

    @GetMapping("staff/totalWaitToRespond100")
    public long countWaitToRespondBookings100() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Wait to respond payment (100%)");
        List<BookingDto> respondBookings = responseEntity.getBody();
        return respondBookings.size();
    }

    @GetMapping("staff/totalWaitToRespond80")
    public long countWaitToRespondBookings80() {
        ResponseEntity<List<BookingDto>> responseEntity = getStatusBooking("Wait to respond payment (80%)");
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

    @PutMapping("staff/respond80/{bookingID}")
    public ResponseEntity<String> respondBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Wait To Respond (80%)");
        return ResponseEntity.ok("Active");
    }

    @PostMapping("/sendCancelEmail/")
    public void sendCancelEmail(@RequestParam int bookingID){
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        SimpleMailMessage msg = new SimpleMailMessage();
        AccountEntity accountEntity = accountService.getAccountById(booking.getAccID().getAccID());
        ProductEntity productEntity = productService.getProductById(booking.getProductID().getProductID());
        float respondPayment = 0f;
        if(booking.getBookingStatus().equals("Wait to respond payment (80%)")) respondPayment = 0.2f;

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(accountEntity.getAccEmail());
            helper.setSubject("You have successfully canceled Booking " + productEntity.getProductName()+".");

            String content = "<html><body>"
                    + "<p>Dear " + accountEntity.getAccName() + ",</p>"
                    + "<p>You have successfully canceled Booking</strong>.</p>"
                    + "<p>Your booking details:</p>"
                    + "<ul>"
                    + "<li>Booking ID: " + booking.getBookingID() + "</li>"
                    + "<li>Start: " + booking.getStartDate() + "</li>"
                    + "<li>End: " + booking.getEndDate() + "</li>"
                    + "<li>Address: " + productEntity.getProductAddress() + "</li>"
                    + "<li>Person: " + booking.getBookingPerson() + "</li>"
                    + "<li>Total: " + booking.getBookingPrice() + " VND</li>"
                    + "<li>The amount you are refunded is: " + (booking.getBookingPrice() - (booking.getBookingPrice() * respondPayment))+ "VND</li>"
                    + "</ul>"
                    + "<p>Best regards,<br/>BookingHomeStay</p>"
                    + "<br/>"
                    + "<p>If you have any questions, please respond to this email!</p>"
                    + "</body></html>";

            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    } @PostMapping("/sendBookingEmail/")
    public void sendBookingEmail(@RequestParam int bookingID,@RequestParam  String title,@RequestParam  String title2){
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        SimpleMailMessage msg = new SimpleMailMessage();
        AccountEntity accountEntity = accountService.getAccountById(booking.getAccID().getAccID());
        ProductEntity productEntity = productService.getProductById(booking.getProductID().getProductID());
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(accountEntity.getAccEmail());
            helper.setSubject(title + productEntity.getProductName());
            String content = "<html><body>"
                    + "<p>Dear " + accountEntity.getAccName() + ",</p>"
                    + "<p>"+title2 + productEntity.getProductName()+"</p>"
                    + "<p>Your booking details:</p>"
                    + "<ul>"
                    + "<li>Booking ID: " + booking.getBookingID() + "</li>"
                    + "<li>Start: " + booking.getStartDate() + "</li>"
                    + "<li>End: " + booking.getEndDate() + "</li>"
                    + "<li>Address: " + productEntity.getProductAddress() + "</li>"
                    + "<li>Person: " + booking.getBookingPerson() + "</li>"
                    + "<li>Total: " + booking.getBookingPrice() + " VND</li>"
                    + "</ul>"
                    + "<p>Best regards,<br/>BookingHomeStay</p>"
                    + "<br/>"
                    + "<p>If you have any questions, please respond to this email!</p>"
                    + "</body></html>";

            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        ProductEntity productEntity = productService.getProductById(booking.getProductID().getProductID());
        sendBookingEmail(bookingID, "Thank! Your reservation at " + productEntity.getProductName()
                + " has been confirmed.","Thank you for your reservation at "+ productEntity.getProductName());
        return ResponseEntity.ok("Done");
    }
    @PutMapping("staff/cancel/{bookingID}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"Cancelled");
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
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        ProductEntity productEntity = productService.getProductById(booking.getProductID().getProductID());
        sendBookingEmail(bookingID, "Booking " + productEntity.getProductName()
                + " is not accepted.","Your booking has failed!!");
        return ResponseEntity.ok("Done");
    }

    public ResponseEntity<List<BookingDto>> getStatusBookingAcc(int accID, String status1, String status2) {
        List<BookingEntity> statusProducts = bookingService.getBookingsByStatusByAccount(accID,status1,status2);
        return ResponseEntity.ok(convertToDtoList(statusProducts));
    }
    public ResponseEntity<List<BookingDto>> getStatusBookingAccV2(int accID, String status1, String status2, String status3, String status4) {
        List<BookingEntity> statusProducts = bookingService.getBookingsByStatusByAccountV2(accID,status1,status2, status3, status4);
        return ResponseEntity.ok(convertToDtoList(statusProducts));
    }
    @GetMapping("customer/waitToByAccId/{accID}")
    public ResponseEntity<List<BookingDto>> getWaitToConfirm(@PathVariable int accID) {
        return getStatusBookingAccV2(accID,"Wait To Confirm", "Wait to confirm (request cancel)", "Rejected", "xxx");
    }
    @GetMapping("customer/waitToRespond-Active/{accID}")
    public ResponseEntity<List<BookingDto>> getWaitToRespondActive(@PathVariable int accID) {
        return getStatusBookingAcc(accID,"Wait to respond", "Active");
    }

    //Trả về list những booking có 1 trong 4 status "Wait to respond", "Active", "In progress", "Done"
    @GetMapping("customer/waitToRespond-Active-Done-In_progress/{accID}")
    public ResponseEntity<List<BookingDto>> getwaitToRespondActiveDoneInprogress(@PathVariable int accID) {
        return getStatusBookingAccV2(accID,"Wait to respond", "Active", "In progress","Done");
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

    @PutMapping("staff/In_progress/{bookingID}")
    public ResponseEntity<String> in_progressBooking(@PathVariable int bookingID) {
        bookingService.statusBooking(bookingID,"In progress");
        return ResponseEntity.ok("Done");
    }

    //check date hiện tại có sau end date chưa, nếu sau end date và status là active thì chuyển sang status done
    @PutMapping("staff/change_status_to_done/{bookingID}")
    public ResponseEntity<String> doneBookingV2(@PathVariable int bookingID) {
        LocalDateTime localDateTime = LocalDateTime.now();
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        if(localDateTime.isAfter(booking.getEndDate())&&booking.getBookingStatus().equalsIgnoreCase("In progress")){
            bookingService.statusBooking(bookingID,"Done");
        }

        return ResponseEntity.ok("Change to Done status");
    }

    //check date hiện tại có trong giữa start-date và end-date ko, nếu có thì chuyển sang status in-progress
    @PutMapping("staff/change_status_to_in_progress/{bookingID}")
    public ResponseEntity<String> inprogressBooking(@PathVariable int bookingID) {
        LocalDateTime localDateTime = LocalDateTime.now();
        BookingEntity booking = bookingService.getBookingByBookingIDV2(bookingID);
        if(localDateTime.isAfter(booking.getStartDate())&&localDateTime.isBefore(booking.getEndDate())&&booking.getBookingStatus().equalsIgnoreCase("active")){
            bookingService.statusBooking(bookingID,"In progress");
        }

        return ResponseEntity.ok("Change to In progress status");
    }

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
    @GetMapping("staff/canceled")
    public ResponseEntity<List<BookingDto>> getCancelBooking() {
        return getStatusBooking("Cancelled");
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
                bookingEntity.getCreateDate(),
                bookingEntity.getBookingPrice(),
                bookingEntity.getBookingPerson(),
                bookingEntity.getBookingStatus(),
                "https://bookinghomestayswp.azurewebsites.net/api/bookings/viewImg/" + bookingEntity.getImgName(),
                new byte[0],
                "https://bookinghomestayswp.azurewebsites.net/api/bookings/paymentRespond/viewImg/" + bookingEntity.getImgRespondName(),
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
        bookingEntity.setCreateDate(bookingDto.getCreateDate());
        bookingEntity.setBookingPrice(bookingDto.getBookingPrice());
        bookingEntity.setBookingPerson(bookingDto.getBookingPerson());
        bookingEntity.setBookingStatus(bookingDto.getBookingStatus());
        bookingEntity.setImgName("https://bookinghomestayswp.azurewebsites.net/api/booking/payment/viewImg/" + bookingEntity.getImgName());
        bookingEntity.setImgData(new byte[0]);
        bookingEntity.setImgRespondName("https://bookinghomestayswp.azurewebsites.net/api/booking/paymentRespond/viewImg/" + bookingEntity.getImgRespondName());
        bookingEntity.setImgRespondData(new byte[0]);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccID(bookingDto.getAccID());
        bookingEntity.setAccID(accountEntity);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductID(bookingDto.getProductID());
        bookingEntity.setProductID(productEntity);

        return bookingEntity;
    }


}