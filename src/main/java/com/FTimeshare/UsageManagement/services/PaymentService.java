package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.PaymentDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.PaymentEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;
    public String createPayment(MultipartFile file,
                                String accountName,
                                String banking,
                                String accountNumber,
                                int accId) throws IOException {
            AccountEntity accountEntity = accountRepository.findById(accId)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " +accId));


            PaymentEntity paymentEntity = paymentRepository.save(PaymentEntity.builder()
                    .accountName(accountName)
                    .banking(banking)
                    .accountNumber(accountNumber)
                    .imgName(file.getName())
                    .imgData(ImageService.compressImage(file.getBytes()))
                    .accID(accountEntity)
                    .build());

            return "Create payment successfully";
        }

        public byte[] downloadImage(String fileName){
            Optional<PaymentEntity> dbImageData = paymentRepository.findByImgName(fileName);
            return ImageService.decompressImage(dbImageData.get().getImgData());
        }

    public List<PaymentDto> getAllBookings() {
        List<PaymentEntity> payments = paymentRepository.findAll();
        return payments.stream()
                .map(paymentEntity -> new PaymentDto(
                        paymentEntity.getPaymentID(),
                        paymentEntity.getAccountName(),
                        paymentEntity.getBanking(),
                        paymentEntity.getAccountNumber(),
                        "https://bookinghomestayswp.azurewebsites.net/api/payment/viewImg/" + paymentEntity.getImgName(),  // Thêm imgName vào đường dẫn
                        new byte[0],
                        paymentEntity.getAccID().getAccID()))
                .collect(Collectors.toList());
    }

    public List<PaymentDto> getBookingsByAccountId(int accId) {
        List<PaymentEntity> bookingEntities = paymentRepository.findByAccID_AccID(accId);
        return bookingEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PaymentDto convertToDto(PaymentEntity paymentEntity) {
        // Your existing DTO conversion logic
        return new PaymentDto(
                paymentEntity.getPaymentID(),
                paymentEntity.getAccountName(),
                paymentEntity.getBanking(),
                paymentEntity.getAccountNumber(),
                "https://bookinghomestayswp.azurewebsites.net/api/payment/viewImg/" + paymentEntity.getImgName(),  // Thêm imgName vào đường dẫn
                new byte[0],
                paymentEntity.getAccID().getAccID());
    }

    public PaymentDto editPayment(int paymentID, PaymentDto updatedPayment, MultipartFile file) throws IOException {
        PaymentEntity existingPayment = paymentRepository.findById(paymentID)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + paymentID));

        String originalFilename = file.getOriginalFilename();
        String filename = originalFilename;
        int counter = 1;

        while (paymentRepository.existsByImgName(filename)) {
            // If it does, append a counter to the filename and try again
            filename = originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                    + "_" + counter
                    + originalFilename.substring(originalFilename.lastIndexOf('.'));
            counter++;
        }


        existingPayment.setAccountName(updatedPayment.getAccountName());
        existingPayment.setBanking(updatedPayment.getBanking());
        existingPayment.setAccountNumber(updatedPayment.getAccountNumber());
        existingPayment.setImgName(filename);
        existingPayment.setImgData(ImageService.compressImage(file.getBytes()));

        PaymentEntity savedPayment = paymentRepository.save(existingPayment);

        return convertToDto(savedPayment);
    }



}


