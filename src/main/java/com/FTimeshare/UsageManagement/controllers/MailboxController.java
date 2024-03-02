package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.MailboxDto;
import com.FTimeshare.UsageManagement.services.MailboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/mailbox")
public class MailboxController {
    @Autowired
    private MailboxService mailboxService;

    @PostMapping("/submitMail")
    public ResponseEntity<?> submitMail(@RequestBody MailboxDto mailboxDto) {
        LocalDateTime now = LocalDateTime.now();
        mailboxDto.setMailboxSendDate(now);
        MailboxDto submitMail = mailboxService.submitMail(mailboxDto);
        return new ResponseEntity<>(submitMail, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteMail/{mailboxId}")
    public ResponseEntity<?> deleteMailBox(@PathVariable int mailboxId) {
        MailboxDto deletedMail = mailboxService.deleteMailBox(mailboxId);

        if (deletedMail != null) {
            return ResponseEntity.ok(deletedMail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
