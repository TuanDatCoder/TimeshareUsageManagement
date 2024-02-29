package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.PaymentEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.BookingRepository;
import com.FTimeshare.UsageManagement.repositories.PaymentRepository;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
@Autowired
private BookingRepository bookingRepository;
@Autowired
    private  AccountRepository accountRepository;
@Autowired
    private  ProductRepository productRepository;
@Autowired
private PaymentRepository paymentRepository;

    public float getSumPriceByProductId(int productID){
        return bookingRepository.sumPriceByProductID(productID);
    }

    public void closeBooking(int bookingID, String Status) {
        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingID);
        if (optionalBooking.isPresent()) {
            BookingEntity booking = optionalBooking.get();
            booking.setBookingStatus(Status);
            bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại với ID: " + bookingID);
        }
    }



    public List<BookingDto> getAllBookings() {
        List<BookingEntity> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(bookingEntity -> new BookingDto(
                        bookingEntity.getBookingID(),
                        bookingEntity.getStartDate(),
                        bookingEntity.getEndDate(),
                        bookingEntity.getBookingPrice(),
                        bookingEntity.getBookingPerson(),
                        bookingEntity.getBookingRating(),
                        bookingEntity.getBookingStatus(),
                        bookingEntity.getImgName(),
                        bookingEntity.getImgData(),
                        bookingEntity.getAccID().getAccID(),
                        bookingEntity.getProductID().getProductID(),
                        bookingEntity.getPaymentID().getPaymentID()))

                .collect(Collectors.toList());
    }



    public BookingDto createBooking(BookingDto booking) {


        BookingEntity bookingEntity = new BookingEntity();
        // Set properties of bookingEntity from bookingRequest
        bookingEntity.setStartDate(booking.getStartDate());
        bookingEntity.setEndDate(booking.getEndDate());
        bookingEntity.setBookingPrice(booking.getBookingPrice());
        bookingEntity.setBookingPerson(booking.getBookingPerson());
        bookingEntity.setBookingRating(booking.getBookingRating());
        bookingEntity.setBookingStatus(booking.getBookingStatus());
        bookingEntity.setImgName(booking.getImgName());
        bookingEntity.setImgData(booking.getImgData());
        // Assuming you have UserRepository and ProductRepository
        AccountEntity accountEntity = accountRepository.findById(booking.getAccID()).orElse(null);
        ProductEntity productEntity = productRepository.findById(booking.getProductID()).orElse(null);
        PaymentEntity paymentEntity = paymentRepository.findById(booking.getPaymentID()).orElse(null);

        if (accountEntity != null && productEntity != null && paymentEntity != null ) {
            bookingEntity.setAccID(accountEntity);
            bookingEntity.setProductID(productEntity);
            bookingEntity.setPaymentID(paymentEntity);
            // Set other properties as needed

            // Save the bookingEntity
            BookingEntity savedBookingEntity = bookingRepository.save(bookingEntity);

            // Convert and return the saved entity as DTO
            return convertToDto(savedBookingEntity);
        } else {
            // Handle case where user or product is not found
            return null;
        }
    }

    private BookingDto convertToDto(BookingEntity bookingEntity) {
        // Your existing DTO conversion logic
        return new BookingDto(
                bookingEntity.getBookingID(),
                bookingEntity.getStartDate(),
                bookingEntity.getEndDate(),
                bookingEntity.getBookingPrice(),
                bookingEntity.getBookingPerson(),
                bookingEntity.getBookingRating(),
                bookingEntity.getBookingStatus(),
                bookingEntity.getImgName(),
                bookingEntity.getImgData(),
                bookingEntity.getAccID().getAccID(),
                bookingEntity.getProductID().getProductID(),
                bookingEntity.getPaymentID().getPaymentID());
    }

    public BookingDto deleteBooking(int bookingID) {
        // Tìm đặt phòng theo ID
        Optional<BookingEntity> bookingEntityOptional = bookingRepository.findById(bookingID);

        if (bookingEntityOptional.isPresent()) {
            BookingEntity bookingEntity = bookingEntityOptional.get();

            // Kiểm tra xem người dùng có quyền xóa đặt phòng hay không (tùy thuộc vào logic của bạn)

            // Xóa đặt phòng
            bookingRepository.delete(bookingEntity);

            // Chuyển đổi và trả về DTO của đặt phòng đã xóa
            return convertToDto(bookingEntity);
        } else {
            // Xử lý trường hợp không tìm thấy đặt phòng
            return null;
        }
    }


//    public List<BookingDto> getBookingsByAccountId(int accID) {
//        List<BookingEntity> bookingEntities = bookingRepository.findByAccID_AccID(accID);
//        return bookingEntities.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

    public float getSumPriceByProductId(int productID){
        float sum = 0;
        List<BookingEntity> bookingEntities = bookingRepository.findByProductID(productID);
        for(int i = 0; i<bookingEntities.size(); i++){
            sum+=bookingEntities.get(i).getBookingPrice();
        }
        return sum;
    }

    public List<BookingDto> getBookingsByBookingId(int bookingID) {
        Optional<BookingEntity> bookingEntities = bookingRepository.findById(bookingID);
        return bookingEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Float> getBookingPricesByProductId(int productID) {
        List<BookingEntity> bookings = bookingRepository.findByProductID_ProductID(productID);

        return bookings.stream()
                .map(BookingEntity::getBookingPrice)
                .collect(Collectors.toList());
    }

    public Float getTotalBookingPriceByProductId(int productId) {
        List<Float> bookingPrices = bookingRepository.findBookingPricesByProductID(productId);
        return (float) bookingPrices.stream().mapToDouble(Float::doubleValue).sum();
    }

}



