package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.PictureDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.PictureEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    // upload and downLoad
    @Autowired
    private ProductService productService; // Đảm bảo đã inject ProductService vào đây

    public String uploadImage(MultipartFile file, int productID) throws IOException {
        ProductEntity product = productService.getProductById(productID);

        PictureEntity imageData = pictureRepository.save(PictureEntity.builder()
                .imgName(file.getOriginalFilename())
                .imgData(ImageService.compressImage(file.getBytes()))
                .productID(product)
                .build());

        return "File uploaded successfully: " + file.getOriginalFilename();
    }

    public byte[] downloadImage(String fileName){
        Optional<PictureEntity> dbImageData = pictureRepository.findByImgName(fileName);
        return ImageService.decompressImage(dbImageData.get().getImgData());
    }

    public String updateImage(int pictureId, String imgName, byte[] imgData) {
        Optional<PictureEntity> pictureOptional = pictureRepository.findById(pictureId);
        if (pictureOptional.isPresent()) {
            PictureEntity pictureEntity = pictureOptional.get();
            pictureEntity.setImgName(imgName);
            pictureEntity.setImgData(imgData);
            pictureRepository.save(pictureEntity);
            return "Image updated successfully.";
        } else {
            return "Image with id " + pictureId + " not found.";
        }
    }

    private PictureDto convertToDto(PictureEntity pictureEntity) {
        return new PictureDto(
                pictureEntity.getImgID(),
                pictureEntity.getImgName(),
                pictureEntity.getImgData(),
                pictureEntity.getProductID().getProductID());
    }

    public List<PictureDto> getAllBookings() {
        List<PictureEntity> pictures = pictureRepository.findAll();
        return pictures.stream()
                .map(pictureEntity -> new PictureDto(
                        pictureEntity.getImgID(),
                        pictureEntity.getImgName(),
                        pictureEntity.getImgData(),
                        pictureEntity.getProductID().getProductID()))
                .collect(Collectors.toList());
    }
}
