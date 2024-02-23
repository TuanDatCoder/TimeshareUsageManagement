package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.services.AccountService;
import com.FTimeshare.UsageManagement.services.BookingService;
import com.FTimeshare.UsageManagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ProductService productService;
    //-------------------------- View all -----------------------
    @GetMapping("/admin")
    public ResponseEntity<List<AccountDto>> getAdminUsers() {
        List<AccountEntity> userEntities = accountService.getUsersByRole(1);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/staff")
    public ResponseEntity<List<AccountDto>> getStaffUsers() {
        List<AccountEntity> userEntities = accountService.getUsersByRole(4);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<AccountDto>> getCustomerUsers() {
        List<AccountEntity> userEntities = accountService.getUsersByRole(2);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<AccountDto>> getOwnerUsers() {
        List<AccountEntity> userEntities = accountService.getUsersByRole(3);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }


    //-------------------------- delete -------------------------
    //http://localhost:8080/api/users/delete/10
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        accountService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted successfully.");
    }

    //Tinh tong doanh thu cua user
    @GetMapping("sum_revenue/{userId}")
    public double sumRevenue(@PathVariable int userId) {
        List<ProductEntity> productEntities = productService.getProductsByUserID(userId);
        double sum = 0;
        for(int i = 0; i<productEntities.size(); i++){
            sum+= (double) bookingService.getSumPriceByProductId(productEntities.get(i).getProductID());
        }
        return sum;
    }

    private List<AccountDto> convertToDtoList(List<AccountEntity> userEntities) {
        return userEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AccountDto convertToDto(AccountEntity accountEntity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccID(accountEntity.getAccID());
        accountDto.setAccName(accountEntity.getAccName());
        accountDto.setAccPhone(accountEntity.getAccPhone());
        accountDto.setAccEmail(accountEntity.getAccEmail());
        accountDto.setAccPassword(accountEntity.getAccPassword());
        accountDto.setAccBirthday(accountEntity.getAccBirthday());

        int roleID = 0; // Giá trị mặc định nếu không tìm thấy roleID
        if (accountEntity.getRoleID() != null) {
            // Lấy ID của vai trò từ đối tượng RoleEntity và gán cho roleID
            roleID = accountEntity.getRoleID().getRoleID(); // Giả sử ID của vai trò là một số nguyên
        }
        accountDto.setRoleID(roleID);

        return accountDto;
    }


}

