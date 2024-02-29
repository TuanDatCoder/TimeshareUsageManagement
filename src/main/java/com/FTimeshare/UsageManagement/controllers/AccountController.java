package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllStatus() {
        List<String> statuses = accountService.getAllStatus();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    //----------------------- Count Total Account --------------------

    @GetMapping("/staff/count/{roleName}")
    public ResponseEntity<Integer> countUsersByRoleName(@PathVariable String roleName) {
        int count = accountService.countUsersByRoleName(roleName);
        return ResponseEntity.ok(count);
    }

    //-------------------------- View all -----------------------
    @GetMapping("/{roleName}")
    public ResponseEntity<List<AccountDto>> getUsersByRoleName(@PathVariable String roleName) {
        List<AccountEntity> userEntities = accountService.getUsersByRoleName(roleName);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    //-------------------------- delete -------------------------
    //http://localhost:8080/api/users/delete/10
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        accountService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted successfully.");
    }


    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("Avatar") MultipartFile file,
                                         @RequestParam String accName,
                                         @RequestParam String accPhone,
                                         @RequestParam String accEmail,
                                         @RequestParam String accPassword,
                                         @RequestParam String accStatus,
                                         @RequestParam Date accBirthday,
                                         @RequestParam int roleID) throws IOException {

        String uploadImage = accountService.uploadImage(file,accName,accPhone,accEmail,accPassword,accStatus,accBirthday,roleID);

        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping("/viewImg/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=accountService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

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
        accountDto.setImgName(accountEntity.getImgName());
        accountDto.setImgData(accountEntity.getImgData());
        int roleID = 0; // Giá trị mặc định nếu không tìm thấy roleID
        if (accountEntity.getRoleID() != null) {
            // Lấy ID của vai trò từ đối tượng RoleEntity và gán cho roleID
            roleID = accountEntity.getRoleID().getRoleID(); // Giả sử ID của vai trò là một số nguyên
        }
        accountDto.setRoleID(roleID);

        return accountDto;
    }

    public AccountEntity convertToEntity(AccountDto accountDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccName(accountDto.getAccName());
        accountEntity.setAccPhone(accountDto.getAccPhone());
        accountEntity.setAccEmail(accountDto.getAccEmail());
        accountEntity.setAccPassword(accountDto.getAccPassword());
        accountEntity.setAccBirthday(accountDto.getAccBirthday());
        accountEntity.setAccStatus(accountDto.getAccStatus());
        accountEntity.setAccImg(accountDto.getAccImg());
        int roleID = 0; // Giá trị mặc định nếu không tìm thấy roleID
        if (accountEntity.getRoleID() != null) {
            // Lấy ID của vai trò từ đối tượng RoleEntity và gán cho roleID
            roleID = accountEntity.getRoleID().getRoleID(); // Giả sử ID của vai trò là một số nguyên
        }
        accountDto.setRoleID(roleID);

        return accountEntity;
    }

}

