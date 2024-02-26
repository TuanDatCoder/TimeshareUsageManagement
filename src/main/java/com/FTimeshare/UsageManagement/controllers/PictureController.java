package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.PictureDto;
import com.FTimeshare.UsageManagement.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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


}
