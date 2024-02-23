package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    //bi loi but i don't know why
//    @Query("SELECT SUM(b.bookingPrice) FROM BookingEntity b where b.productID.productID= :productID")
//    double sumPriceByProductID(@Param("productID") int productID);

    @Query("SELECT b FROM BookingEntity b where b.productID.productID= :productID")
    List<BookingEntity> findByProductID(@Param("productID") int productID);

}
