package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.MailboxDto;
import com.FTimeshare.UsageManagement.entities.MailboxEntity;
import com.FTimeshare.UsageManagement.services.MailboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/mailbox")
public class  MailboxController {
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

    @GetMapping("/view")
    public ResponseEntity<List<MailboxDto>> getAllMailbox() {
        List<MailboxEntity> newsEntities = mailboxService.getAllMailbox();
        List<MailboxDto> newsDto = newsEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(newsDto);
    }

    //View theo accid

    // View nguoi nhan
    @GetMapping("sendByAccId/{user_id}")
    public ResponseEntity<List<MailboxDto>> getMailboxByAccIdSend(@PathVariable int user_id) {
        List<MailboxEntity> mailboxEntities = mailboxService.getMailboxByAccIdSend(user_id);
        return ResponseEntity.ok(convertToDtoList(mailboxEntities));
    }
    @GetMapping("receiveByAccId/{user_id}")
    public ResponseEntity<List<MailboxDto>> getMailboxByAccIdReceive(@PathVariable int user_id) {
        List<MailboxEntity> mailboxEntities = mailboxService.getMailboxByAccIdReceive(user_id);
        return ResponseEntity.ok(convertToDtoList(mailboxEntities));
    }

    private MailboxDto convertToDto(MailboxEntity mailboxEntity) {
        MailboxDto mailboxDto = new MailboxDto();
        mailboxDto.setMailboxId(mailboxEntity.getMailboxId());
        mailboxDto.setMailboxDescription(mailboxEntity.getMailboxDescription());
        mailboxDto.setMailboxType(mailboxEntity.getMailboxType());
        mailboxDto.setMailboxStatus(mailboxEntity.getMailboxStatus());
        mailboxDto.setMailboxSendDate(mailboxEntity.getMailboxSendDate());
        mailboxDto.setAccIdSend(mailboxEntity.getAccIdSend().getAccID());
        mailboxDto.setAccIdReceive(mailboxEntity.getAccIdReceive().getAccID());
        return mailboxDto;
    }

    private List<MailboxDto> convertToDtoList(List<MailboxEntity> mailboxEntities) {
        return mailboxEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
