package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.PaymentEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
            byte[] imgData = file.getBytes();

            AccountEntity accountEntity = accountRepository.findById(accId)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " +accId));


            PaymentEntity paymentEntity = paymentRepository.save(PaymentEntity.builder()
                    .accountName(accountName)
                    .banking(banking)
                    .accountNumber(accountNumber)
                    .imgName(file.getOriginalFilename())
                    .imgData(ImageService.compressImage(file.getBytes()))
                    .accId(accountEntity)
                    .build());

            return "File uploaded successfully: " + file.getOriginalFilename();
        }

        public byte[] downloadImage(String fileName){
            Optional<PaymentEntity> dbImageData = paymentRepository.findByImgName(fileName);
            return ImageService.decompressImage(dbImageData.get().getImgData());
        }

    }



//    public byte[] downloadImage(String fileName){
//        Optional<AccountEntity> dbImageData = accountRepository.findByImgName(fileName);
//        return ImageService.decompressImage(dbImageData.get().getImgData());
//    }
//
//}



