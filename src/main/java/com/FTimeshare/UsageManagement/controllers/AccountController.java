package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.services.AccountService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "https://pass-timeshare.vercel.app")
@CrossOrigin(origins = "https://pass-timeshare-tuandat-frontends-projects.vercel.app")
@RequestMapping("/api/users")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping("/staffview")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/staff/accounts/delete")
    public ResponseEntity<String> deleteAccountsByAccIDs(@RequestBody List<Integer> accIDs) {
        accountService.deleteAccountsByAccIDs(accIDs);
        return ResponseEntity.status(HttpStatus.OK).body("Accounts deleted successfully");
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllStatus() {
        List<String> statuses = accountService.getAllStatus();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @GetMapping("viewDetail/{accID}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable int accID) {
        AccountEntity accountEntity = accountService.getAccountById(accID);
        if (accountEntity != null) {
            AccountDto accountDto = convertToDto(accountEntity);
            return ResponseEntity.ok(accountDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<AccountDto>> getStatusAccount(String status) {
        List<AccountEntity> statusAccounts = accountService.getAccountsByStatus(status);
        return ResponseEntity.ok(convertToDtoList(statusAccounts));
    }

    //Total:
    @GetMapping("staff/totalActive")
    public int countActiveAccounts() {
        ResponseEntity<List<AccountDto>> responseEntity = getStatusAccount("Active");
        List<AccountDto> activeAcc = responseEntity.getBody();
        return activeAcc.size();
    }
    @GetMapping("staff/totalBlock")
    public int countBlockAccounts() {
        ResponseEntity<List<AccountDto>> responseEntity = getStatusAccount("Block");
        List<AccountDto> blockAcc = responseEntity.getBody();
        return blockAcc.size();
    }

    //Change
    @PutMapping("staff/active/{accID}")
    public ResponseEntity<String> activeAccount(@PathVariable int accID) {
        accountService.statusAccount(accID,"Active");
        return ResponseEntity.ok("Done");
    }
    @PutMapping("/verify/active/{email}")
    public ResponseEntity<String> activeAccount(@PathVariable String email) {
        accountService.statusAccountEmail(email,"Active");
        return ResponseEntity.ok("Done");
    }
    @PutMapping("staff/block/{accID}")
    public ResponseEntity<String> blockAccount(@PathVariable int accID) {
        accountService.statusAccount(accID,"Block");
        return ResponseEntity.ok("Done");
    }

    //view
    @GetMapping("staff/active")
    public ResponseEntity<List<AccountDto>> getActive() {
        return getStatusAccount("Active");
    }
    @GetMapping("staff/block")
    public ResponseEntity<List<AccountDto>> getBlock() {
        return getStatusAccount("Block");
    }

    @GetMapping("/staff/count/{roleName}")
    public ResponseEntity<Integer> countUsersByRoleName(@PathVariable String roleName) {
        int count = accountService.countUsersByRoleName(roleName);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<List<AccountDto>> getUsersByRoleName(@PathVariable String roleName) {
        List<AccountEntity> userEntities = accountService.getUsersByRoleName(roleName);
        return ResponseEntity.ok(convertToDtoList(userEntities));
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        accountService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted successfully.");
    }
    @DeleteMapping("deleteByEmail/{email}")
    public ResponseEntity<String> deleteEmail(@PathVariable String email) {
        accountService.deleteAccountByEmail(email);
        return ResponseEntity.ok("User with Email " + email + " has been deleted successfully.");
    }

    @PostMapping
    public String CreateAccount(@RequestParam("Avatar") MultipartFile file,
                                           @RequestParam String accName,
                                           @RequestParam String accPhone,
                                           @RequestParam String accEmail,
                                           @RequestParam String accPassword,
                                           @RequestParam String accStatus,
                                           @RequestParam Date accBirthday,
                                           @RequestParam int roleID) throws IOException {

        if (accountService.isEmailExists(accEmail)) {
            return "Email already exists";
        }
        String createAccount = accountService.uploadImage(file,accName,accPhone,accEmail,accPassword,accStatus,accBirthday,roleID);
        if (createAccount.equals("File uploaded successfully") || createAccount.equals("already exists"))
            return accEmail;

        return "Account creation failed";
    }

    @PostMapping("/sendOTP/")
    public void sendOTP(@RequestParam int getOTP, @RequestParam String email){

        SimpleMailMessage msg = new SimpleMailMessage();
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Email Verification - "+getOTP);

            String content = "<html><body>"
                    + "<p>Please enter this OTP code to check your email to register for the bookinghomestay page.</p>"
                    + "<p>Best regards,<br/>BookingHomeStay</p>"
                    + "</body></html>";

            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/viewImg/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=accountService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PutMapping("/edit/{accountID}")
    public ResponseEntity<?> editAccount(
            @PathVariable int accountID,
            @RequestParam("Avatar") MultipartFile file,
            @RequestParam String accName,
            @RequestParam String accPhone,
            @RequestParam String accEmail,
            @RequestParam String accPassword,
            @RequestParam String accStatus,
            @RequestParam Date accBirthday,
            @RequestParam int roleID) {

        AccountDto updatedAccount = AccountDto.builder()
                .accName(accName)
                .accPhone(accPhone)
                .accEmail(accEmail)
                .accPassword(accPassword)
                .accStatus(accStatus)
                .accBirthday(accBirthday)
                .roleID(roleID)
                .build();

        try {
            AccountDto editedAccount = accountService.editAccount(accountID, updatedAccount, file);
            return ResponseEntity.ok(editedAccount);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating account: " + e.getMessage());
        }
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
        accountDto.setAccStatus(accountEntity.getAccStatus());
        accountDto.setImgName( "https://bookinghomestayswp.azurewebsites.net/api/users/viewImg/"+ accountEntity.getImgName());
        accountDto.setImgData(new byte[0]);
        int roleID = 0;
        if (accountEntity.getRoleID() != null) {
            roleID = accountEntity.getRoleID().getRoleID();
        }
        accountDto.setRoleID(roleID);

        return accountDto;
    }


}