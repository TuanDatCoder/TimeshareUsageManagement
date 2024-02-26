package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.PictureDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.PictureEntity;
import com.FTimeshare.UsageManagement.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PictureService {
    @Autowired
    private PictureRepository pictureRepository;

    public List<PictureDto> viewPictureByProductID(int productID) {
        List<PictureEntity> pictureEntities = pictureRepository.findByProductID_ProductID(productID);
        return pictureEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private PictureDto convertToDto(PictureEntity pictureEntity) {
        return new PictureDto(
                pictureEntity.getImgID(),
                pictureEntity.getImgName(),
                pictureEntity.getImgUrl(),
                pictureEntity.getProductID().getProductID());
    }

    public List<PictureDto> getAllBookings() {
        List<PictureEntity> pictures = pictureRepository.findAll();
        return pictures.stream()
                .map(pictureEntity -> new PictureDto(
                        pictureEntity.getImgID(),
                        pictureEntity.getImgName(),
                        pictureEntity.getImgUrl(),
                        pictureEntity.getProductID().getProductID()))
                .collect(Collectors.toList());
    }
}
