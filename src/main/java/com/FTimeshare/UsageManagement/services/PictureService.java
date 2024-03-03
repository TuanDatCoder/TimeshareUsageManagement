package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.dtos.PictureDto;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.PictureEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.PictureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
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
    private ProductService productService;

//    public String uploadImage(MultipartFile file, int productID) throws IOException {
//        if (productService.getProductById(productID) == null) {
//            System.out.println("Product not found with ID: " + productID);
//            return "Product not found with ID: " + productID;
//        }
//
//        // Kiểm tra xem tên ảnh đã tồn tại trong cơ sở dữ liệu chưa
//        Optional<PictureEntity> existingPicture = pictureRepository.findByImgName(file.getOriginalFilename());
//        if (existingPicture.isPresent()) {
//            System.out.println("Image with name " + file.getOriginalFilename() + " already exists.");
//            return "Image with name " + file.getOriginalFilename() + " already exists.";
//        }
//
//        // Lưu ảnh vào cơ sở dữ liệu nếu không trùng tên
//        PictureEntity imageData = pictureRepository.save(PictureEntity.builder()
//                .imgName(file.getOriginalFilename())
//                .imgData(ImageService.compressImage(file.getBytes()))
//                .productID(productService.getProductById(productID))
//                .build());
//        return "File uploaded successfully: " + file.getOriginalFilename();
//    }




    public List<String> uploadImages(MultipartFile[] files, int productID) throws IOException {
        List<String> uploadResults = new ArrayList<>();

        for (MultipartFile file : files) {
            String uploadResult = uploadImage(file, productID);
            uploadResults.add(uploadResult);
        }

        return uploadResults;
    }

    public String uploadImage(MultipartFile file, int productID) throws IOException {
        if (productService.getProductById(productID) == null) {
            System.out.println("Product not found with ID: " + productID);
            return "Product not found with ID: " + productID;
        }

        // Kiểm tra xem tên ảnh đã tồn tại trong cơ sở dữ liệu chưa
        Optional<PictureEntity> existingPicture = pictureRepository.findByImgName(file.getOriginalFilename());
        if (existingPicture.isPresent()) {
            System.out.println("Image with name " + file.getOriginalFilename() + " already exists.");
            return "Image with name " + file.getOriginalFilename() + " already exists.";
        }

        // Lưu ảnh vào cơ sở dữ liệu nếu không trùng tên
        PictureEntity imageData = pictureRepository.save(PictureEntity.builder()
                .imgName(file.getOriginalFilename())
                .imgData(ImageService.compressImage(file.getBytes()))
                .productID(productService.getProductById(productID))
                .build());
        return "File uploaded successfully: " + file.getOriginalFilename();
    }


    public byte[] downloadImage(String fileName){
        Optional<PictureEntity> dbImageData = pictureRepository.findByImgName(fileName);
        return ImageService.decompressImage(dbImageData.get().getImgData());
    }

    public ResponseEntity<?> updateImage(MultipartFile file, int imgID) throws IOException {
        Optional<PictureEntity> existingPicture = pictureRepository.findById(imgID);
        if (!existingPicture.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Picture not found with ID: " + imgID);
        }

        // Kiểm tra xem tên ảnh đã tồn tại trong cơ sở dữ liệu chưa
        Optional<PictureEntity> existingPictureWithName = pictureRepository.findByImgName(file.getOriginalFilename());
        if (existingPictureWithName.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Image with name " + file.getOriginalFilename() + " already exists.");
        }

        PictureEntity picture = existingPicture.get();
        picture.setImgName(file.getOriginalFilename());
        picture.setImgData(ImageService.compressImage(file.getBytes()));
        pictureRepository.save(picture);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Image updated successfully.");
    }

    private PictureDto convertToDto(PictureEntity pictureEntity) {
        return new PictureDto(
                pictureEntity.getImgID(),
                "http://localhost:8080/api/pictures/imgView/"+pictureEntity.getImgName(),
                new byte[0],
                pictureEntity.getProductID().getProductID());
    }

    public List<PictureDto> getAllBookings() {
        List<PictureEntity> pictures = pictureRepository.findAll();
        return pictures.stream()
                .map(pictureEntity -> new PictureDto(
                        pictureEntity.getImgID(),
                        "http://localhost:8080/api/pictures/imgView/"+pictureEntity.getImgName(),
                        new byte[0],
                        pictureEntity.getProductID().getProductID()))
                .collect(Collectors.toList());
    }
}
