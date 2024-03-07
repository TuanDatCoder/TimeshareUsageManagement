package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.PictureDto;

import com.FTimeshare.UsageManagement.services.PictureService;
import com.FTimeshare.UsageManagement.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/pictures")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @GetMapping("/viewPicture/{productID}")
    public ResponseEntity<List<PictureDto>> viewPictureByProductID(@PathVariable int productID) {
        List<PictureDto> pictures = pictureService.viewPictureByProductID(productID);
        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    @GetMapping("/customerview")
    public ResponseEntity<List<PictureDto>> getAllBookings() {
        List<PictureDto> pictures = pictureService.getAllBookings();
        return ResponseEntity.ok(pictures);
    }



    @PostMapping("/{productID}")
    public ResponseEntity<?> uploadImages(@PathVariable int productID, @RequestParam("pictures") MultipartFile[] files) throws IOException {
        List<String> uploadResults = pictureService.uploadImages(files, productID);
        return ResponseEntity.status(HttpStatus.OK).body(uploadResults);
    }

    @GetMapping("imgView/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=pictureService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @PutMapping("/updateImg/{imgID}")
    public ResponseEntity<?> updateImage(@PathVariable int imgID, @RequestParam("pictures") MultipartFile file) {
        try {
            ResponseEntity<?> response = pictureService.updateImage(file, imgID);
            return response;
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating image: " + e.getMessage());
        }
    }


}
