package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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


    @Query("SELECT b FROM BookingEntity b WHERE b.bookingStatus  Like CONCAT('%', :status1, '%') OR b.bookingStatus = :status2")
    List<BookingEntity> findByBookingStatus2(String status1, String status2);

    Optional<BookingEntity> findByImgName(String fileName);
    Optional<BookingEntity> findByImgRespondName(String fileName);
    @Query("SELECT b FROM BookingEntity b WHERE b.bookingStatus Like :bookingStatus AND b.productID.productID = :productID")
    List<BookingEntity> findBookingEntityByBookingStatusAndProductID(String bookingStatus, int productID);

    @Query("SELECT b FROM BookingEntity b WHERE b.accID.accID = :accID AND(b.bookingStatus  Like CONCAT('%', :status1, '%') OR b.bookingStatus  Like CONCAT('%', :status2, '%'))")
    List<BookingEntity>  findByBookingStatusAAndAccID(int accID, String status1, String status2);

    @Query("SELECT b FROM BookingEntity b WHERE b.accID.accID = :accID AND(b.bookingStatus  Like CONCAT('%', :status1, '%') OR b.bookingStatus  Like CONCAT('%', :status2, '%') OR b.bookingStatus  Like CONCAT('%', :status3, '%') OR b.bookingStatus  Like CONCAT('%', :status4, '%'))")
    List<BookingEntity>  findByBookingStatusAAndAccIDV2(int accID, String status1, String status2, String status3, String status4);
    @Query("SELECT b FROM BookingEntity b WHERE b.accID.accID = :accID and b.bookingStatus = :status")
    List<BookingEntity> findByAccIDAAndBookingStatus(int accID, String status);

    void deleteAllByAccIDIn(List<Integer> accIDs);



}
