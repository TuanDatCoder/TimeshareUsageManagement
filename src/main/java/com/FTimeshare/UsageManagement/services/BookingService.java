package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.BookingDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.BookingEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.BookingRepository;
import com.FTimeshare.UsageManagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
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



    public void statusBooking(int bookingID, String Status) {
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
                        bookingEntity.getCreateDate(),
                        bookingEntity.getBookingPrice(),
                        bookingEntity.getBookingPerson(),
                        bookingEntity.getBookingStatus(),
                        "http://localhost:8080/api/payment/viewImg/" + bookingEntity.getImgName(),  // Thêm imgName vào đường dẫn
                        new byte[0],
                        bookingEntity.getAccID().getAccID(),
                        bookingEntity.getProductID().getProductID(), new byte[0]))

                .collect(Collectors.toList());
    }

    public Boolean checkBooking(BookingDto bookingDto){
        List<BookingEntity> bookings = getBookingsByStatusAndProductId("Active", bookingDto.getProductID());
        LocalDateTime reqStartDate = bookingDto.getStartDate();
        LocalDateTime reqEndDate = bookingDto.getEndDate();

        for (BookingEntity b: bookings) {
            if(reqStartDate.isAfter(b.getStartDate())&&reqStartDate.isBefore(b.getEndDate()))
                return false;
            if (reqEndDate.isAfter(b.getStartDate())&&reqEndDate.isBefore(b.getEndDate()))
                return false;
            if(reqStartDate.isBefore(b.getStartDate())&&reqEndDate.isAfter(b.getEndDate()))
                return false;
        }
        return true;
    }

    public BookingDto createBooking(BookingDto booking,MultipartFile file) throws IOException {


        BookingEntity bookingEntity = new BookingEntity();
        // Set properties of bookingEntity from bookingRequest
        bookingEntity.setStartDate(booking.getStartDate());
        bookingEntity.setEndDate(booking.getEndDate());
        LocalDateTime localDateTime = LocalDateTime.now();
        bookingEntity.setCreateDate(localDateTime);
        Duration duration = Duration.between(booking.getStartDate(), booking.getEndDate());
        long days = duration.toDays();

        bookingEntity.setBookingPerson(booking.getBookingPerson());
        bookingEntity.setBookingStatus("Wait to confirm");
        bookingEntity.setImgName(file.getOriginalFilename());
        bookingEntity.setImgData(ImageService.compressImage(file.getBytes()));
        AccountEntity accountEntity = accountRepository.findById(booking.getAccID()).orElse(null);
        ProductEntity productEntity = productRepository.findById(booking.getProductID()).orElse(null);
        bookingEntity.setBookingPrice(productEntity.getProductPrice()*days);
        if (accountEntity != null && productEntity != null  ) {
            bookingEntity.setAccID(accountEntity);
            bookingEntity.setProductID(productEntity);
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

    public ResponseEntity<?> uploadBookingPaymentPicture(MultipartFile file, int bookingID) throws IOException {
        BookingEntity booking = getBookingByBookingIDV2(bookingID);

        booking.setImgName(file.getOriginalFilename());
        booking.setImgData(ImageService.compressImage(file.getBytes()));
        booking.setBookingStatus("Wait to confirm");
        bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Booking Payment Picture submit successfully.");
    }

    public ResponseEntity<?> uploadBookingRespondPaymentPicture(MultipartFile file, int bookingID) throws IOException {
        BookingEntity booking = getBookingByBookingIDV2(bookingID);

        booking.setRespondPaymentImg(ImageService.compressImage(file.getBytes()));
        bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Booking Respond Payment Picture submit successfully.");
    }

    private BookingDto convertToDto(BookingEntity bookingEntity) {
        // Your existing DTO conversion logic
        return new BookingDto(
                bookingEntity.getBookingID(),
                bookingEntity.getStartDate(),
                bookingEntity.getEndDate(),
                bookingEntity.getCreateDate(),
                bookingEntity.getBookingPrice(),
                bookingEntity.getBookingPerson(),
                bookingEntity.getBookingStatus(),
                "http://localhost:8080/api/payment/viewImg/" + bookingEntity.getImgName(),  // Thêm imgName vào đường dẫn
                new byte[0],
                bookingEntity.getAccID().getAccID(),
                bookingEntity.getProductID().getProductID(), new byte[0]);
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


    public List<BookingDto> getBookingsByAccountId(int accID) {
        List<BookingEntity> bookingEntities = bookingRepository.findByAccID_AccID(accID);
        return bookingEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public float getSumPriceByProductId(int productID){
        float sum = 0;
        List<BookingEntity> bookingEntities = bookingRepository.findByProductID(productID);
        for(int i = 0; i<bookingEntities.size(); i++){
            if(bookingEntities.get(i).getBookingStatus().equals("Done")) {
                sum += bookingEntities.get(i).getBookingPrice();
            }
        }
        return sum;
    }

    public List<BookingDto> getBookingsByBookingId(int bookingID) {
        Optional<BookingEntity> bookingEntities = bookingRepository.findById(bookingID);
        return bookingEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingEntity getBookingByBookingIDV2(int bookingID){
        BookingEntity bookingEntity = bookingRepository.findBookingEntityByBookingID(bookingID);
        return  bookingEntity;
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

    public List<BookingEntity> getBookingsByStatus(String status) {
        return bookingRepository.findByBookingStatus(status);
    }

    public List<BookingEntity> getBookingsByStatusByAccount(int accID, String status1, String status2) {
        return bookingRepository.findByBookingStatusAAndAccID(accID, status1, status2);
    }
    public List<BookingEntity> getBookingsByStatus2(String status1, String status2) {
        return bookingRepository.findByBookingStatus2(status1, status2);
    }

    public byte[] downloadImage(String fileName) {
        Optional<BookingEntity> dbImageData = bookingRepository.findByImgName(fileName);
        return ImageService.decompressImage(dbImageData.get().getImgData());
    }


    public List<BookingEntity> getBookingsByStatusAndProductId(String status, int productID) {
        return bookingRepository.findBookingEntityByBookingStatusAndProductID(status, productID);
    }

    public List<BookingDto> getBookingsByAccountId_Status(int accID, String status) {
        List<BookingEntity> bookingEntities = bookingRepository.findByAccIDAAndBookingStatus(accID, status);
        return bookingEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        //return bookingRepository.findByAccIDAAndBookingStatus(accID, done);
    }


}