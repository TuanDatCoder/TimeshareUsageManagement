package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {


    List<BookingEntity> findByAccID_AccID(int accID);

    @Query("SELECT b FROM BookingEntity b where b.productID.productID= :productID")
    List<BookingEntity> findByProductID(@Param("productID") int productID);
}
