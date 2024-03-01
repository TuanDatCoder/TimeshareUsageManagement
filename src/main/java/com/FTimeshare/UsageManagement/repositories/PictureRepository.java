package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.entities.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PictureRepository extends JpaRepository<PictureEntity, Integer> {
    List<PictureEntity> findByProductID_ProductID(int productID);
    Optional<PictureEntity> findByImgName(String fileName);

}
