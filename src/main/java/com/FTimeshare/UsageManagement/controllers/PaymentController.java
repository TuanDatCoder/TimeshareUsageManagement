package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.PaymentDto;
import com.FTimeshare.UsageManagement.repositories.PaymentRepository;
import com.FTimeshare.UsageManagement.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestParam("ImgBanking") MultipartFile file,
                                           @RequestParam String accountName,
                                           @RequestParam String banking,
                                           @RequestParam String accountNumber,
                                           @RequestParam int accID) throws IOException {

        String uploadImage = paymentService.createPayment(file,accountName,banking,accountNumber,accID);

        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping("/viewImg/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=paymentService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<PaymentDto>> getAllPayment() {
        List<PaymentDto> payments = paymentService.getAllBookings();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/payment/{accId}")
    public ResponseEntity<List<PaymentDto>> viewPaymentsByAccountId(@PathVariable int accId) {
        List<PaymentDto> payments = paymentService.getBookingsByAccountId(accId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PutMapping("/edit/{paymentID}")
    public ResponseEntity<?> editPayment(@PathVariable int paymentID,
                                          @RequestParam("ImgBanking") MultipartFile file,
                                          @RequestParam String accountName,
                                          @RequestParam String banking,
                                          @RequestParam String accountNumber,
                                          @RequestParam int accID) {

        PaymentDto updatedPayment = PaymentDto.builder()
                .accountName(accountName)
                .banking(banking)
                .accountNumber(accountNumber)
                .accID(accID)
                .build();

        try {
            PaymentDto editedPayment = paymentService.editPayment(paymentID, updatedPayment, file);
            return ResponseEntity.ok(editedPayment);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating account: " + e.getMessage());
        }
    }
}
