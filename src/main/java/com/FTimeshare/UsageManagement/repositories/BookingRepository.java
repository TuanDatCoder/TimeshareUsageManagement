package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
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

    List<BookingEntity> findByProductID_ProductID(int productID);

    @Query("SELECT b FROM BookingEntity b where b.bookingID= :bookingID")
    BookingEntity findBookingEntityByBookingID(@Param("bookingID") int bookingID);

    @Query("SELECT b.bookingPrice FROM BookingEntity b WHERE b.productID.productID = :productID")
    List<Float> findBookingPricesByProductID(int productID);

    List<BookingEntity> findByBookingStatus(String status);

    @Query("SELECT b FROM BookingEntity b WHERE b.bookingStatus = :bookingStatus AND b.productID.productID = :productID")
    List<BookingEntity> findBookingEntityByBookingStatusAndProductID(String bookingStatus, int productID);


    @Query("SELECT b FROM BookingEntity b WHERE b.accID.accID = :accID AND(b.bookingStatus = :status1 OR b.bookingStatus = :status2)")
    List<BookingEntity>  findByBookingStatusAAndAccID(int accID, String status1, String status2);
}
